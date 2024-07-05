package com.itwill.gaebokchi.dto;

import com.itwill.gaebokchi.repository.Pro;

import lombok.Data;

@Data
public class UserProfileDto {
	private String userid;
	private String image;
	private String nickname;
	private String career;
	
	public Pro toEntity() {
		return Pro.builder()
				.image(image)
				.userid(userid)
				.nickname(nickname)
				.career(career)
				.build(); 
	}
}
