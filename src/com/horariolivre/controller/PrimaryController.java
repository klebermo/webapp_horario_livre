package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Usuario;
import com.horariolivre.service.UsuarioService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="acesso")
public class PrimaryController {
	
	@Autowired
	private UsuarioService usuario_service;
	
	@Autowired
	private UsuarioHome usuario;
	
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
	
	@RequestMapping(value="logout")
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("acesso/login");
		return mav;
	}
	
	@RequestMapping(value="start")
	public ModelAndView start(@ModelAttribute("username") String username) {
		Usuario user = usuario.findByUsername(username);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/start");
		mav.addObject("usuario", user);
		return mav;
	}
	
	@RequestMapping(value="perfil")
	public ModelAndView perfil(@ModelAttribute("username") String username) {
		Usuario user = usuario.findByUsername(username);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/perfil");
		mav.addObject("usuario", user);
		mav.addObject("tipos", usuario_service.listaTipos());
		mav.addObject("chave", usuario_service.listaKey());
		mav.addObject("valor", usuario_service.listaValue(user));
		return mav;
	}
	
	@RequestMapping(value="config")
	public ModelAndView config(@ModelAttribute("username") String username) {
		Usuario user = usuario.findByUsername(username);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/config");
		mav.addObject("usuario", user);
		return mav;
	}
	
}
