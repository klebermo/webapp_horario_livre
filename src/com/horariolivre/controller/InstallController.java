package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.service.InstallService;

@Controller
@RequestMapping(value="instala")
public class InstallController {
	
	@Autowired
	private InstallService instala;
	
	@RequestMapping(value="app")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("instala/index");
		return mav;
	}
	
	@RequestMapping(value="createdb", method=RequestMethod.POST)
	public String createdb(@RequestParam("maquina") String maquina, @RequestParam("usuario_db") String usuario, @RequestParam("senha_db") String senha) {
		if(instala.create_database(maquina, usuario, senha))
			return "yes";
		else
			return "not";
	}
	
	@RequestMapping(value="createuser", method=RequestMethod.POST)
	public String createuser(@RequestParam("usuario") String usuario, @RequestParam("senha1") String senha, @RequestParam("primeiroNome") String pnome, @RequestParam("ultimoNome") String unome) {
		if(instala.create_user(usuario, senha, pnome, unome))
			return "yes";
		else
			return "not";
	}
}
