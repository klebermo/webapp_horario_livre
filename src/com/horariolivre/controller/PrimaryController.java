package com.horariolivre.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Tipo;
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
	
	@RequestMapping(value="salvar_perfil", method=RequestMethod.POST)
	public int salvar_perfil(@ModelAttribute("username") String username, @RequestParam("senha1") String senha1, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, WebRequest webrequest) {
		System.out.println("salvar perfil");
		int saida;
		
		String login = usuario.findByUsername(username).getLogin();
		String tipo = webrequest.getParameter("tipo");
		
		String[] key = usuario_service.listaKey();
		String[] value = new String[key.length];
		
		for(int i=0; i<key.length; i++) {
			String valor = webrequest.getParameter(key[i]);
			if(valor == null) {
				System.out.println("valor null");
				value[i] = "";
			}
			else {
				System.out.println("valor="+valor);
				value[i] = valor;
			}
			
			System.out.println("key["+i+"]="+key[i]);
			System.out.println("value["+i+"]="+value[i]);
		}
		
		if (usuario_service.cadastra(login, senha1, pnome, unome, new Tipo(tipo), key, value)) {
			System.out.println("cadastrou");
			saida = 1;
		}
		else {
			System.out.println("nao cadastrou");
			saida = 0;
		}
		
		return saida;
	}
	
	@RequestMapping(value="config")
	public ModelAndView config(@ModelAttribute("username") String username) {
		Usuario user = usuario.findByUsername(username);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/config");
		mav.addObject("usuario", user);
		mav.addObject("config", user.getConfig());
		return mav;
	}
	
	@RequestMapping(value="salvar_config", method=RequestMethod.POST)
	public int salvar_config(@ModelAttribute("username") String username, @RequestParam("hora_inicial") String horaInicial, @RequestParam("hora_final") String horaFinal) {
		int saida;
		
		if (usuario_service.salva_config(parseTime(horaInicial), parseTime(horaFinal)))
			saida = 1;
		else
			saida = 0;
		
		return saida;
	}
	
	private Date parseDate(String data) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
		Date d_inicio = new Date(0);
		try {
			d_inicio = new Date(format1.parse(data).getTime());
			System.out.println("data="+d_inicio.toString());
			return d_inicio;
		} catch (ParseException e) {
			System.out.println("erro no parse de data_inicial");
			e.printStackTrace();
			return null;
		}
	}
	
	private Time parseTime(String hora) {
		SimpleDateFormat format3 = new SimpleDateFormat("HH:mm");  
		Time h_inicio = new Time(0);
		try {
			h_inicio = new Time(format3.parse(hora).getTime());
			System.out.println("hora="+h_inicio.toString());
			return h_inicio;
		} catch (ParseException e) {
			System.out.println("erro no parse de hora_inicial");
			e.printStackTrace();
			return null;
		}
	}
	
}
