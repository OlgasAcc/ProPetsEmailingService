package proPets.emailing.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import proPets.emailing.dto.EmailMatchedPostsAuthorsDto;
import proPets.emailing.dto.EmailNewPostAuthorDto;
import proPets.emailing.exceptions.PostNotFoundException;

@Service
@Slf4j
public class EmailingDataExchangeService {
	
	@Autowired
	DataExchange dataExchange;
	
	@Autowired
	EmailService emailService;

	@StreamListener(DataExchange2.ALL_MATCHED)
	public void handleNewPostAuthorData(@Payload EmailNewPostAuthorDto toNewPostAuthor) throws JsonMappingException, JsonProcessingException, PostNotFoundException {
		log.info("Received postMQDto: {}", toNewPostAuthor);
		String postId = toNewPostAuthor.getPostId();
		String flag = toNewPostAuthor.getFlag();
		String to = toNewPostAuthor.getEmail();
		try {
			emailService.sendMessageToNewPostAuthor(postId, flag, to);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@StreamListener(DataExchange.NEW_MATCHED)
	public void handleAllMatchedPostsAuthorData(@Payload EmailMatchedPostsAuthorsDto toAllMatchedPostsAuthors) throws JsonMappingException, JsonProcessingException, PostNotFoundException {
		log.info("Received postMQDto: {}", toAllMatchedPostsAuthors);
		String postId = toAllMatchedPostsAuthors.getPostId();
		String flag = toAllMatchedPostsAuthors.getFlag();
		String[] to = toAllMatchedPostsAuthors.getEmails();
		try {
			emailService.sendMessageToMatchingPostsAuthors(postId, flag, to);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
