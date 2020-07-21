package proPets.emailing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proPets.emailing.configuration.BeanConfiguration;
import proPets.emailing.configuration.EmailingConfiguration;
import proPets.emailing.service.EmailService;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/send/v1/notification")

public class EmailngServiceController {

	@Autowired
	EmailService emailService;

	@Autowired
	EmailingConfiguration emailingConfiguration;

	@RefreshScope
	@GetMapping("/config")
	public BeanConfiguration getRefreshedData() {
		return new BeanConfiguration(emailingConfiguration.getHostEmail(), emailingConfiguration.getBaseUrl(),
				emailingConfiguration.getAccessCodeBaseUrl());
	}

}
