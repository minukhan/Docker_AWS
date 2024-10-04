package com.example.backapp.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backapp.auth.TokenProvider;

@RestController
@CrossOrigin(origins = "*")
public class MemberController {
	@Autowired
	private MemberService service;
	
	@Autowired
	private TokenProvider provider;

	@Autowired
	private AuthenticationManagerBuilder abuilder;

	@PostMapping("/join")
	public Map join(MemberDto dto) {
		boolean flag = true;
		try {
			service.save(dto);
		} catch (Exception e) {
			flag = false;
			System.out.println(e);
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@PostMapping("/login")
	public Map login(String id, String pwd) {
		//인증에 사용할 객체. Username / Password 를 비교하여 인증하는 클래스
		UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(id, pwd);
		
		//authenticate() 인증 메서드. 인증한 결과를 Authentication에 담아 반환
		Authentication auth = abuilder.getObject().authenticate(authtoken);
		
		//isAuthenticated(): 인증결과 반환(true/false)
		boolean flag = auth.isAuthenticated();
		System.out.println("인증결과:" + flag);
		Map map = new HashMap();
		if (flag) {
			//인증 성공시 토큰 생성
			String token = provider.getToken(service.getMember(id));
			//토큰을 요청자에게 전달
			map.put("token", token);
			map.put("id", id);
			String type = provider.getRoles(token);
			map.put("type", type);
		}
		map.put("flag", flag);
		return map;
	}
	
	//내정보확인
	@GetMapping("/auth/member")
	public Map get() {
		Map map = new HashMap();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName(); // username 추출
		MemberDto dto = service.getMember(id);
		map.put("dto", dto);
		return map;
	}
	
}
