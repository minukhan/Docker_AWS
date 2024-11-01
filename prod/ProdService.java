package com.example.demo.prod;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdService {
	@Autowired
	private ProdDao dao;
	
	public ProdDto save(ProdDto dto) {
		Prod entity = dao.save(new Prod(dto.getNum(), dto.getName()));
		return new ProdDto(entity.getNum(), entity.getName());
	}
	
	public ArrayList<ProdDto> getAll(){
		List<Prod> l = dao.findAll();
		ArrayList<ProdDto> list = new ArrayList<>();
		for(Prod entity: l) {
			list.add(new ProdDto(entity.getNum(), entity.getName()));
		}
		return list;
	}
}
