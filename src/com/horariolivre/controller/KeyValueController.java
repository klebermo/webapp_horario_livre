package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.horariolivre.service.AtributoService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="key")
public class KeyValueController {
	
	@Autowired
	private AtributoService key;

	@RequestMapping(value="cadastra_campo", method=RequestMethod.GET)
	public boolean cadastra_campo(@ModelAttribute("username") String username, @RequestParam("campo") String campo) {
		if(key.temAutorizacao(key.getUsuarioByUsername(username).getId())) {
			return key.cadastra(campo);
		}
		else {
			return false;
		}
	}
	
	@RequestMapping(value="remove_campo", method=RequestMethod.GET)
	public boolean remove_campo(@ModelAttribute("username") String username, @RequestParam("campo") String campo) {
		if(key.temAutorizacao(key.getUsuarioByUsername(username).getId())) {
			return key.remover(campo);
		}
		else {
			return false;
		}
	}
	
	
}
