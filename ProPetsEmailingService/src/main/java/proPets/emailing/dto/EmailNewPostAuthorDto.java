package proPets.emailing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class EmailNewPostAuthorDto {
	String postId;
	String flag;
	String email;
}
