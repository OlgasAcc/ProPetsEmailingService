package proPets.emailing.service;

import java.io.IOException;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import proPets.emailing.configuration.EmailingConfiguration;

@Service
@EnableBinding({ DataExchange.class, DataExchange2.class })
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailingConfiguration emailingConfiguration;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	EmailingDataExchangeService dataExchangeService;

	@Override
	public void sendMessageToNewPostAuthor(String postId, String flag, String to)
			throws MessagingException, IOException {

		// !!! здесь будет линк не на сервер напрямую, а на соответствующую страницу
		// (фронт) на сайте для запроса на поиск и отрисовку постов
		// добавить в ссылку одноразовый код, параллельно отправить его в базу ЛФ для
		// сохранения (синхр)

		String accessCode = generateAccessCode();
		// String url = "https://propets..../"
		String url = "http://localhost:8081/" + flag + "/v1/" + "all_matched" + "?postId=" + postId + "&flag=" + flag
				+ "&accessCode=" + accessCode;
		String text = "<html>Hi!<br>Using our searching algorythm we've tried to find matches to your post. Hope it would be helpful to you. But if the list is empty - don't be upsed and try tommorow. We'll notify you about every matched updates. <br>Click this link and check it now:<br><br>";

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("proPets.manager@gmail.com");
		helper.setTo(to);
		message.setSubject("Current searching results for your post!");

		message = addAttachmentToMessage(message, url, text, to);

		try {
			emailSender.send(message);
		} catch (MailException m) {
			m.printStackTrace();
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
		// добавить в ссылку одноразовый код, параллельно отправить его в базу ЛФ для
		// сохранения (синхр)

		String accessCode = generateAccessCode();
		// String url = "https://propets..../"
		String url = "http://localhost:8081/" + flag + "/v1/" + "new_matched" + "?postId=" + postId + "&flag=" + flag
				+ "&accessCode=" + accessCode;
		String text = "<html>Hi!<br>Just now we've got some new post that would be matched to your post. Hope it would be helpful to you. But if not - don't be upset, try to search manually with our site's filters. We'll notify you about every matched updates.<br>Click this link and check it now:<br><br>";

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("proPets.manager@gmail.com");
		message.setSubject("New matched post!");

		// message = addAttachmentToMessage(message, url, text,to);
		for (int i = 0; i < to.length; i++) {
			message = addAttachmentToMessage(message, url, text, to[i]);
			helper.setTo(to[i]);
			emailSender.send(message);
		}

	}

	private MimeMessage addAttachmentToMessage(MimeMessage message, String url, String text, String to)
			throws MessagingException, IOException {

		StringBuffer body = new StringBuffer(text);
		body.append(url);
		body.append("<br><br><br>");

		// adding signature:
		body.append("<img src=\"cid:image\" width=\"30%\" height=\"30%\" /><br>");
		body.append("Let every lost friend will be found!<br><br>");
		body.append("Best regards, <br>ProPets team.<br>");
		body.append("www.propets.co.il<br><br>");
		body.append("<a href=\"https:\\/\\/propets.co.il\\/unsubscribe\\/{to}\">Unsubscribe</a><br>");
		body.append("</html>");

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(body.toString(), "text/html");

		MimeBodyPart imageBodyPart = new MimeBodyPart();
		imageBodyPart = new MimeBodyPart();
		DataSource fds = new FileDataSource("E:\\Programming\\PROJECT\\logo.jpeg");
		imageBodyPart.setDataHandler(new DataHandler(fds));
		imageBodyPart.setHeader("Content-ID", "<image>");
		imageBodyPart.setDisposition(MimeBodyPart.INLINE);

		MimeMultipart content = new MimeMultipart();
		content.addBodyPart(messageBodyPart);
		content.addBodyPart(imageBodyPart);
		message.setContent(content);

		return message;
	}

	private String generateAccessCode() {
		String uuid = UUID.randomUUID().toString();
		return "uuid = " + uuid;
	}
}
