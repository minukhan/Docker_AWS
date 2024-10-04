package com.example.backapp.cafe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class CafeprodController {
	@Autowired
	private CafeprodService service;

	@Value("${spring.servlet.multipart.location}")
	private String path;

	@PostMapping("/auth/cafe") // => index_seller로 이동
	public Map add(CafeprodDto dto) {
		boolean flag = true;
		CafeprodDto c = service.save(dto);// 상품등록. 이미지 정보 빠짐. 상품번호 할당됨.
		String oname = dto.getF().getOriginalFilename();
		String fname = c.getNum() + oname; // 1이미지명.jpg
		File f = new File(path + fname);
		try {
			dto.getF().transferTo(f);// 업로드 파일 복사
			c.setFname(f.getName());
			service.save(c);// 수정. 이미지 정보 추가
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@GetMapping("/auth/cafe/get/{num}") // 한 매장의 상품목록(상품명/가격) view 경로:cafe/list
	public Map get(@PathVariable("num") int num) {
		Map map = new HashMap();
		map.put("dto", service.getProd(num));
		return map;
	}

	@GetMapping("/auth/cafe/{seller}") // 한 매장의 상품목록(상품명/가격) view 경로:cafe/list
	public Map list(@PathVariable("seller") String seller) {
		Map map = new HashMap();
		map.put("list", service.getbyStore(seller));
		return map;
	}

	@PutMapping("/auth/cafe")
	public Map edit(CafeprodDto dto) {
		boolean flag = true;
		try {
			CafeprodDto dto2 = service.getProd(dto.getNum());
			dto2.setName(dto.getName());
			dto2.setPrice(dto.getPrice());
			service.save(dto2);
		} catch (Exception e) {
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	@GetMapping("/read-img/{fname}")
	public ResponseEntity<byte[]> read_img(@PathVariable("fname")String fname) {
		ResponseEntity<byte[]> result = null;
		File f = new File(path + fname);
		HttpHeaders header = new HttpHeaders(); //import org.springframework.http.HttpHeaders;
		try {
			header.add("Content-Type", Files.probeContentType(f.toPath()));
			result = new ResponseEntity<byte[]>(
					FileCopyUtils.copyToByteArray(f),header, HttpStatus.OK
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
