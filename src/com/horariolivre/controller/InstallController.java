package com.horariolivre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="instala")
public class InstallController {
	
	@RequestMapping(value="app")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("instala/index");
		return mav;
	}
}
