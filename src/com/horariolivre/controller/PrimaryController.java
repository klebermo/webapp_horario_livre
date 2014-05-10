package com.horariolivre.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
		mav.addObject("instalado", this.testConection());
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
	
	private boolean testConection() {
		Properties props = new Properties();
		FileInputStream fos;
		try {
			fos = new FileInputStream( "database.properties" );
			props.load(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String url = props.getProperty("jdbc.url");
		String user = props.getProperty("jdbc.user");
		String pass = props.getProperty("jdbc.pass");
		
		int counter = 0;
		try {
			Connection conn = DriverManager.getConnection(url,user,pass);
			Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT count(*) FROM pg_catalog.pg_database WHERE datname = 'horario'");
		    rs.next();
		    counter  = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(counter > 0)
			return true;
		else
			return false;
	}

}
