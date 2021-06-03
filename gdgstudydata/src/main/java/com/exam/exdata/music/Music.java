package com.exam.exdata.music;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Music {
	@Id
	private Long id;
	private String title;
	private String artist;
	private LocalDate regdate;
	private String url;
}
