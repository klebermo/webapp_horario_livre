package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.dao.DadosHome;
import com.horariolivre.dao.DadosUsuarioHome;
import com.horariolivre.dao.TipoHome;
import com.horariolivre.dao.TipoUsuarioHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Usuario;

@Controller
@RequestMapping(value="acesso")
public class PrimaryController {
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private TipoHome tipo;
	
	@Autowired
	private TipoUsuarioHome tipo_usuario;
	
	@Autowired
	private DadosHome key;
	
	@Autowired
	private DadosUsuarioHome valor;
	
	private int id_usuario;
	
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
	public ModelAndView start() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/start");
		return mav;
	}
	
	@RequestMapping(value="perfil")
	public ModelAndView perfil() {
		Usuario atual = usuario.findById(id_usuario);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/perfil");
		mav.addObject("usuario", atual);
		mav.addObject("tipos", tipo.findALL());
		mav.addObject("key", key.findALL());
		mav.addObject("value", valor.findByUsuario(atual));
		return mav;
	}
	
	@RequestMapping(value="config")
	public ModelAndView config() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/config");
		mav.addObject("config", usuario.findById(id_usuario).getConfig());
		return mav;
	}
	
}
