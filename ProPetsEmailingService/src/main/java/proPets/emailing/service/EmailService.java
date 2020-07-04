package proPets.emailing.service;

import java.io.IOException;

import javax.mail.MessagingException;

public interface EmailService {

	void sendMessageToNewPostAuthor(String postId, String flag, String to) throws MessagingException, IOException;

	void sendMessageToMatchingPostsAuthors(String postId, String flag, String[] to) throws MessagingException, IOException;


}
