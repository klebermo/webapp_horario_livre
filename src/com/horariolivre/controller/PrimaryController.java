package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.horariolivre.service.UsuarioService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="acesso")
public class PrimaryController {
	
	@Autowired
	private UsuarioService usuario;
	
	@RequestMapping(value="login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("acesso/login");
		return mav;
	}
	
	@RequestMapping(value="erro-login")
	public ModelAndView erro_login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("erro/login");
		return mav;
	}
	
	@RequestMapping(value="erro-nao-autorizado")
	public ModelAndView erro_nao_autorizado() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("erro/nao_autorizado");
		return mav;
	}
	
	@RequestMapping(value="erro-nao-configurado")
	public ModelAndView erro_nao_configurado() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("erro/nao_configurado");
		return mav;
	}
	
	@RequestMapping(value="logout")
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("acesso/login");
		return mav;
	}
	
	@RequestMapping(value="start")
	public ModelAndView start(@ModelAttribute("username") String username) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/start");
		mav.addObject("usuario", usuario.getUsuarioByUsername(username));
		return mav;
	}

}
