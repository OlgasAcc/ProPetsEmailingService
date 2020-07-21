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

	public BeanConfiguration(String hostEmail, String baseUrl, String accessCodeBaseUrl) {
		this.hostEmail = hostEmail;
		this.baseUrl = baseUrl;
		this.accessCodeBaseUrl = accessCodeBaseUrl;
	}
}
