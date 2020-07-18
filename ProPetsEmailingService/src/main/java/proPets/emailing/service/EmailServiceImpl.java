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

		// !!! здесь будет линк не на сервер напрямую, а на соответствующую страницу
		// (фронт) на сайте для запроса на поиск и отрисовку постов

		String accessCode = utils.generateAccessCode();
		// String url = "https://propets..../"
		String url = "http://localhost:8081/" + flag + "/v1/" + "all_matched" + "?postId=" + postId + "&flag=" + flag
				+ "&accessCode=" + accessCode;
		System.out.println(url);
		String text = "<html>Hi!<br>Using our searching algorythm we've tried to find matches to your post. Hope it would be helpful to you. But if the list is empty - don't be upsed and try tommorow. We'll notify you about every matched updates. <br>Click this link and check it now:<br><br>";

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("proPets.manager@gmail.com");
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
		// !!! здесь будет линк не на сервер напрямую, а на соответствующую страницу
		// (фронт) на сайте для запроса на поиск и отрисовку постов

		String accessCode = utils.generateAccessCode();
		// String url = "https://propets..../"
		String url = "http://localhost:8081/" + flag + "/v1/" + "new_matched" + "?postId=" + postId + "&flag=" + flag
				+ "&accessCode=" + accessCode;
		System.out.println(url);
		String text = "<html>Hi!<br>Just now we've got some new post that would be matched to your post. Hope it would be helpful to you. But if not - don't be upset, try to search manually with our site's filters. We'll notify you about every matched updates.<br>Click this link and check it now:<br><br>";

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("proPets.manager@gmail.com");
		message.setSubject("New matched post!");

		// message = addAttachmentToMessage(message, url, text,to);
		if (utils.saveAccessCodeInLostFoundServiceDB(accessCode).getStatusCode() == HttpStatus.OK) {
			for (int i = 0; i < to.length; i++) {
				message = utils.addAttachmentToMessage(message, url, text, to[i]);
				helper.setTo(to[i]);
				emailSender.send(message);
			}
		}
	}
	
	
}
