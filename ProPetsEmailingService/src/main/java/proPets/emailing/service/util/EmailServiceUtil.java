package proPets.emailing.service.util;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import proPets.emailing.configuration.EmailingConfiguration;

@Component
public class EmailServiceUtil {
	
	@Autowired
	EmailingConfiguration emailingConfiguration;

	public MimeMessage addAttachmentToMessage(MimeMessage message, String url, String text, String to)
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

	public String generateAccessCode() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	public ResponseEntity<String> saveAccessCodeInLostFoundServiceDB (String accessCode) {
		RestTemplate restTemplate = emailingConfiguration.restTemplate();

		//String url = "https://propets-.../security/v1/post";
		String url = "http://localhost:8081/lost/v1/accessCode"; //to LF service
		try {
			HttpHeaders newHeaders = new HttpHeaders();
			newHeaders.add("Content-Type", "application/json");
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
					.queryParam("accessCode",accessCode);
			RequestEntity<String> request = new RequestEntity<String>(newHeaders, HttpMethod.POST, builder.build().toUri());
			ResponseEntity<String> newResponse = restTemplate.exchange(request, String.class);
			return newResponse;
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Saving code is failed");
		}
	} 
	
	
}
