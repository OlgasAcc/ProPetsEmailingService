package proPets.emailing.service;

import javax.mail.MessagingException;

public interface EmailService {

	void sendMessageToNewPostAuthor(String postId, String flag, String to);

	void sendMessageToMatchingPostsAuthors(String postId, String flag, String[] to);

	void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
			throws MessagingException;

}
