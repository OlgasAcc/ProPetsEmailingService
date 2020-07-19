package proPets.emailing.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@RefreshScope
public class EmailingConfiguration {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Value("${host.email}")
	String hostEmail;
	
	@RefreshScope
	public String getHostEmail() {
		return hostEmail;
	}
	
	@Value("${base.url}")
	String baseUrl;
	
	@RefreshScope
	public String getBaseUrl() {
		return baseUrl;
	}
	

}