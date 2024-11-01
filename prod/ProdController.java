package com.example.demo.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProdController {
	@Autowired
	private ProdService service;
	
	@GetMapping("")
	public String home(Model m) {
		m.addAttribute("list", service.getAll());
		return "list";
	}
	
	@GetMapping("/add")
	public String addForm(){
		return "add";
	}
	
	@PostMapping("/add")
	public String add(ProdDto dto){
		service.save(dto);
		return "redirect:/";
	}
}
