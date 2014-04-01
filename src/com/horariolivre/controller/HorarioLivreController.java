package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.dao.HorarioLivreHome;
import com.horariolivre.dao.UsuarioHome;

@Controller
@RequestMapping(value="horario")
public class HorarioLivreController {
	
	@Autowired
	private HorarioLivreHome horariolivre;
	
	@Autowired
	private UsuarioHome usuario;

	@RequestMapping(value="cadastra")
	public ModelAndView cadastra() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("horario/cadastra");
		return mav;
	}
	
	@RequestMapping(value="remove")
	public ModelAndView remove() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("horario/remove");
		return mav;
	}
	
	@RequestMapping(value="altera")
	public ModelAndView altera() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("horario/altera");
		return mav;
	}
	
	@RequestMapping(value="lista")
	public ModelAndView lista() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("horario/lista");
		return mav;
	}
}
