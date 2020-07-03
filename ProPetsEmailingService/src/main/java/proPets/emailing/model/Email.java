package proPets.emailing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Email { //тот пост, что приходит из ЛФ для конвертации (пост из Монго)
	
	String id; //post id
	String email; //authorId
	String flag;
	String type;
	String address;
	String distinctiveFeatures;
	String[] picturesURLs;

}
