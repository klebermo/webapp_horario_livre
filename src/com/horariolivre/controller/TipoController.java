package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.horariolivre.service.TipoService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="tipo")
public class TipoController {
	
	@Autowired
	private TipoService tipoUsuario;

	@RequestMapping(value="cadastra_tipo", method=RequestMethod.GET)
	public boolean cadastra_tipo(@ModelAttribute("username") String username, @RequestParam("tipo") String tipo) {
		if(tipoUsuario.temAutorizacao(tipoUsuario.getUsuarioByUsername(username).getId())) {
			return tipoUsuario.cadastra(tipo);
		}
		else {
			return false;
		}
	}
	
	@RequestMapping(value="remove_tipo", method=RequestMethod.GET)
	public boolean remove_tipo(@ModelAttribute("username") String username, @RequestParam("tipo") String tipo) {
		if(tipoUsuario.temAutorizacao(tipoUsuario.getUsuarioByUsername(username).getId())) {
			return tipoUsuario.remover(tipo);
		}
		else {
			return false;
		}
	}
	
	
}
