package com.example.backapp.cafe;

import java.util.ArrayList;
import java.util.List;

import com.example.backapp.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CafeprodService {
	@Autowired
	private CafeprodDao dao;
	
	public CafeprodDto save(CafeprodDto dto) {
		Cafeprod cp = dao.save(new Cafeprod(dto.getNum(), dto.getName(), dto.getPrice(), dto.getSeller(),
				dto.getFname()));
		return new CafeprodDto(cp.getNum(), cp.getName(), cp.getPrice(), cp.getSeller(), cp.getFname(), null);
	}
	
	
	public CafeprodDto getProd(int num) {
		Cafeprod cp = dao.findById(num).orElse(null);
		if(cp == null) {
			return null;
		}
		return new CafeprodDto(cp.getNum(), cp.getName(), cp.getPrice(), cp.getSeller(), cp.getFname(), null);
	}

	public ArrayList<CafeprodDto> getbyStore(String seller){
		System.out.println("service seller:"+seller);
		List<Cafeprod> l = dao.findBySeller(new Member(seller, "", "", "",""));
		ArrayList<CafeprodDto> list = new ArrayList<CafeprodDto>();
		for(Cafeprod cp:l) {
			list.add(new CafeprodDto(cp.getNum(), cp.getName(), cp.getPrice(), cp.getSeller(), cp.getFname(), null));
		}
		return list;
	}
	
	public void delProd(int num) {
		dao.deleteById(num);
	}
}
