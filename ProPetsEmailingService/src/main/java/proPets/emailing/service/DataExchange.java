package proPets.emailing.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DataExchange {
	
	String NEW_MATCHED = "newMatched";
	
	@Input(NEW_MATCHED)
    SubscribableChannel forAllMatchedPostsAuthors();
	
}
