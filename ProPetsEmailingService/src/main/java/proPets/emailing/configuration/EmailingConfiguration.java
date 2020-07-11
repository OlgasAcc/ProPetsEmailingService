package proPets.emailing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ManagedResource
public class EmailingConfiguration {

	//Map<String, EmailDto> posts = new ConcurrentHashMap<>();
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	//@Value("${imagga.url}")
	//String url;
	
	//public String getUrl() {
	//	return url;
	//}
	

}