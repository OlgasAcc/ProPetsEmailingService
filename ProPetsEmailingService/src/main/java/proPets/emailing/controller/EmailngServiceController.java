package proPets.emailing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proPets.emailing.dto.EmailDto;
import proPets.emailing.dto.PostRequestDto;
import proPets.emailing.service.EmailService;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/convert/v1")

public class EmailngServiceController {

	@Autowired
	EmailService converterService;

	@PutMapping ("/post")
	public ResponseEntity<EmailDto> convertPost(@RequestBody PostRequestDto postRequestDto) throws Exception {
		System.out.println("im in email service");

		return null;
	}
	
}
