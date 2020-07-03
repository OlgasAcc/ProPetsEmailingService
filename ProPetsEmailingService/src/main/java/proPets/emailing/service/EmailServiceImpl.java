package proPets.emailing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proPets.emailing.configuration.EmailingConfiguration;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailingConfiguration converterConfiguration;


	

}
