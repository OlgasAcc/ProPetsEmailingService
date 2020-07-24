package proPets.emailing.configuration;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@Getter

public class BeanConfiguration {
	private String hostEmail;
	private String baseUrl;
	private String accessCodeBaseUrl;
	private String version;
	private String textToNewPostAuthor;
	private String textToMatchedPostAuthor;
	
	public BeanConfiguration(String hostEmail, String baseUrl, String accessCodeBaseUrl, String version,
			String textToNewPostAuthor, String textToMatchedPostAuthor) {
		this.hostEmail = hostEmail;
		this.baseUrl = baseUrl;
		this.accessCodeBaseUrl = accessCodeBaseUrl;
		this.version = version;
		this.textToNewPostAuthor = textToNewPostAuthor;
		this.textToMatchedPostAuthor = textToMatchedPostAuthor;
	}
}
