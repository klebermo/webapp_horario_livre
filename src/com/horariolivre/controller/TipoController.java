package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.horariolivre.service.TipoService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="tipo")
public class TipoController {
	
	@Autowired
	private TipoService tipo;

	@RequestMapping(value="cadastra_tipo", method=RequestMethod.GET)
	@ResponseBody
	public String cadastra_tipo(@ModelAttribute("username") String username, @RequestParam("nome") String tipoUsuario) {
		if(tipo.temAutorizacao(tipo.getUsuarioByUsername(username).getId())) {
			if (tipo.cadastra(tipoUsuario))
				return "yes";
			else
				return "not";
		}
		else {
			return "no_permit";
		}
	}
	
	@RequestMapping(value="remove_tipo", method=RequestMethod.GET)
	@ResponseBody
	public String remove_tipo(@ModelAttribute("username") String username, @RequestParam("nome") String tipoUsuario) {
		if(tipo.temAutorizacao(tipo.getUsuarioByUsername(username).getId())) {
			if (tipo.remover(tipoUsuario))
				return "yes";
			else
				return "not";
		}
		else {
			return "not_permit";
		}
	}
	
	
}