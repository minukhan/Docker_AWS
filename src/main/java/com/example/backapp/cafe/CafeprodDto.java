package com.example.backapp.cafe;

import com.example.backapp.member.Member;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CafeprodDto {
	private int num;
	private String name;
	private int price;
	private Member seller;
	private String fname;
	private MultipartFile f;
}
