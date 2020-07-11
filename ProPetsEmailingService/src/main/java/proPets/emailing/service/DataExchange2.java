package proPets.emailing.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DataExchange2 {
	
	String ALL_MATCHED = "allMatched";
	
	@Input(ALL_MATCHED)
	 SubscribableChannel forNewPostAuthor();

}
