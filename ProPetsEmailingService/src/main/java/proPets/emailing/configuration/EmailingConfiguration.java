package proPets.emailing.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RefreshScope
public class EmailingConfiguration {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
//	@Bean
//	public ObjectMapper objectMapper() {
//		return new ObjectMapper();
//	}
	
	@Value("${current.version}")
	String version;
	
	@RefreshScope
	public String getVersion() {
		return version;
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
	
	@Value("${accessCode.lostFound.base.url}")
	String accessCodeBaseUrl;
	
	@RefreshScope
	public String getAccessCodeBaseUrl() {
		return accessCodeBaseUrl;
	}
	
	@Value("${text.newPostAuthor}")
	String textToNewPostAuthor;
	
	@RefreshScope
	public String getTextToNewPostAuthor() {
		return textToNewPostAuthor;
	}
	
	@Value("${text.matchedPostAuthor}")
	String textToMatchedPostAuthor;
	
	@RefreshScope
	public String getTextToMatchedPostAuthor() {
		return textToMatchedPostAuthor;
	}

}