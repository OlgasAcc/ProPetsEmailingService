package proPets.emailing.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import proPets.emailing.configuration.EmailingConfiguration;
import proPets.emailing.service.util.EmailServiceUtil;

@Service
@EnableBinding({ DataExchange.class, DataExchange2.class })
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailingConfiguration emailingConfiguration;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	EmailingDataExchangeService dataExchangeService;
	
	@Autowired
	EmailServiceUtil utils;

	@Override
	public void sendMessageToNewPostAuthor(String postId, String flag, String to)
			throws MessagingException, IOException {

		String accessCode = utils.generateAccessCode();
		String url = emailingConfiguration.getBaseUrl() + flag + "/" + emailingConfiguration.getVersion() + "/" + "all_matched" + "?postId=" + postId + "&flag=" + flag
				+ "&accessCode=" + accessCode;
		System.out.println(url);
		String text = emailingConfiguration.getTextToNewPostAuthor();

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(emailingConfiguration.getHostEmail());
		helper.setTo(to);
		message.setSubject("Current searching results for your post!");

		message = utils.addAttachmentToMessage(message, url, text, to);
		if (utils.saveAccessCodeInLostFoundServiceDB(accessCode).getStatusCode() == HttpStatus.OK) {
			try {
				emailSender.send(message);
			} catch (MailException m) {
				m.printStackTrace();
			}
		}
	}

	@Override
	public void sendMessageToMatchingPostsAuthors(String postId, String flag, String[] to)
			throws MessagingException, IOException {
		if (to.length == 0 || to == null) {
			return;
		}

		String accessCode = utils.generateAccessCode();
		String url = emailingConfiguration.getBaseUrl() + flag + "/" + emailingConfiguration.getVersion() + "/" + "new_matched" + "?postId=" + postId + "&flag=" + flag
				+ "&accessCode=" + accessCode;
		System.out.println(url);
		String text = emailingConfiguration.getTextToMatchedPostAuthor();

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(emailingConfiguration.getHostEmail());
		message.setSubject("New matched post!");

		if (utils.saveAccessCodeInLostFoundServiceDB(accessCode).getStatusCode() == HttpStatus.OK) {
			for (int i = 0; i < to.length; i++) {
				message = utils.addAttachmentToMessage(message, url, text, to[i]);
				helper.setTo(to[i]);
				emailSender.send(message);
			}
		}
	}
	
	
}
