package proPets.emailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostRequestDto { //тот пост, что приходит из ЛФ для конвертации (пост из Монго)
	
	String id; //post id
	String email; //authorId
	String flag;
	String type;
	String address;
	String distinctiveFeatures;
	String[] picturesURLs;

}
