package proPets.emailing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import proPets.emailing.dto.EmailMatchedPostsAuthorsDto;
import proPets.emailing.dto.EmailNewPostAuthorDto;
import proPets.emailing.service.EmailService;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/send/v1/notification")

public class EmailngServiceController {

	@Autowired
	EmailService emailService;

	@Async
	@PutMapping ("/all_matched")
	public void notificateNewPostAuthor(@RequestParam ("postId") String postId, @RequestParam ("flag") String flag, @RequestBody EmailNewPostAuthorDto emailDto) throws Exception {
		System.out.println("im in email service");
		emailService.sendMessageToNewPostAuthor(postId, flag, emailDto.getEmail());
	}
	
	@Async
	@PutMapping ("/new_matched")
	public void notificateMatchesPostsAuthors(@RequestParam ("postId") String postId, @RequestParam ("flag") String flag, @RequestBody EmailMatchedPostsAuthorsDto emailDto) throws Exception {
		System.out.println("im in email service");
		emailService.sendMessageToMatchingPostsAuthors(postId, flag, emailDto.getEmails());
	}
}
