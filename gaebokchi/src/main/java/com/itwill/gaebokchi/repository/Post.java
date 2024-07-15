package com.itwill.gaebokchi.repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class Post {
	private Integer id; // PK
	private String clubType; // 클럽종류
	private String title; // 제목
	private String media;  // 업로드 동영상 경로
	private String content; // 본문
	private String author; // 작성자(users 테이블 PK랑 연결)
	private String category; // P001 (메인 포스트 코드) ->매핑 쿼리문에 입력 해두면 됨
	private Integer views; // 조회수
	private Integer likes; // 좋아요 수
	private Integer height; // 글 작성자의 키
	private Integer career; // 구력 
	private Integer handy; //글 작성자의 평균 핸디
	private Integer ironDistance; // 글 작성자의 7번 아이언 평균 비거리
	private Integer driverDistance; // 글 작성자의 드라이버 평균 비거
	private LocalDateTime createdTime; // 작성 시간
	private LocalDateTime modifiedTime; // 수정 시간
	private String selection;
	private String nickname;
	
	private String mediaPath;
	
    public String getFormattedCreatedTime() {
        if (createdTime != null) {
            // UTC 시간대로 변환
            ZonedDateTime utcDateTime = createdTime.atZone(ZoneId.of("UTC"));
            // 한국 시간대로 변환
            ZonedDateTime seoulDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            // 포맷 정의
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH시mm분");
            // 포맷 적용
            return seoulDateTime.format(formatter);
        } else {
            return null;
        }
    }
    
    public String getFormattedModifiedTime() {
        if (modifiedTime != null) {
            ZonedDateTime utcDateTime = modifiedTime.atZone(ZoneId.of("UTC"));
            ZonedDateTime seoulDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH시mm분");
            return seoulDateTime.format(formatter);
        } else {
            return null;
        }
    }
    
    
	

}
