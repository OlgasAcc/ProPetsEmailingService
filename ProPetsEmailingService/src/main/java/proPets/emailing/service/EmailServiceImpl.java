package proPets.emailing.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import proPets.emailing.configuration.EmailingConfiguration;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailingConfiguration converterConfiguration;

	@Autowired
	private JavaMailSender emailSender;

	// w/o attachment
	@Async
	@Override
	public void sendMessageToNewPostAuthor(String postId, String flag, String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		String text = buildTextForNewPostAuthor (postId, flag);
		message.setFrom("proPets.manager@gmail.com");
		message.setTo(to);
		message.setSubject("Current searching results for your post");
		message.setText(text);
		try {
			emailSender.send(message);
		} catch (MailException m) {
			m.printStackTrace();
		}
	}

	// w/o attachment
	@Async
	@Override
	public void sendMessageToMatchingPostsAuthors(String postId, String flag, String[] to) {
		SimpleMailMessage message = new SimpleMailMessage();
		String text = buildTextForMatchedPostAuthor(postId, flag);
		message.setFrom("proPets.manager@gmail.com");
		message.setSubject("New matched post");
		message.setText(text);
		for (int i = 0; i < to.length; i++) {
			message.setTo(to[i]);
			emailSender.send(message);
		}
	}
	

	private String buildTextForNewPostAuthor(String postId, String flag) {
		//String url = "https://propets..../" + flag + "/v1/" + "all_matched" + "?postId=" + postId + "&flag=" + flag;
		String url = "http://localhost:8081/" + flag + "/v1/" + "all_matched" + "?postId=" + postId + "&flag=" + flag;
		String text = "Hi! Using our searching algorythm we've tried to find matches for your post. Hope it would be helpful to you. But if the list is empty - don't be upsed and try tommorow. We'll notify you about every matched updates. Click this link and check it now: "
				+ System.lineSeparator() + url + System.lineSeparator() + "Let every lost friend will be found"
				+ System.lineSeparator() + "Best regards, ProPets team";
		return text;
	}
	
	private String buildTextForMatchedPostAuthor(String postId, String flag) {
		//String url = "https://propets..../" + flag + "/v1/" + "new_matched" + "?postId=" + postId + "&flag=" + flag;
		String url = "http://localhost:8081/" + flag + "/v1/" + "new_matched" + "?postId=" + postId + "&flag=" + flag;
		String text = "Hi! Just now we've got some new post that would be matched for you. Hope it would be helpful to you. But if not - don't be upsed, try to search manually with our site's filters. We'll notify you about every matched updates. Click this link and check it now: "
				+ System.lineSeparator() + url + System.lineSeparator() + "Let every lost friend will be found"
				+ System.lineSeparator() + "Best regards, ProPets team";
		return text;
	}
	
	

	// with attachment
	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
			throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("proPets.manager@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		helper.addAttachment("Invoice", file);

		emailSender.send(message);

	}
}
