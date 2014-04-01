package com.horariolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.cookie.CookieAccessor;
import com.horariolivre.entity.Usuario;
import com.horariolivre.service.UsuarioService;

@Controller
@RequestMapping(value="usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService usuario;
	
	@RequestMapping(value="cadastra")
	public ModelAndView cadastra() {
		CookieAccessor cookie = new CookieAccessor();
		int id_usuario = cookie.getCookieUsingCookieHandler();
		
		if(usuario.temAutorizacaoCadastro(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("usuario/cadastra");
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
	
	@RequestMapping(value="cadastra_usuario", method=RequestMethod.GET)
	public String cadastra(@RequestParam("login") String login, @RequestParam("senha") String senha, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, @RequestParam("tipo") String tipo, WebRequest webrequest) {
		String saida = new String();
		
		CookieAccessor cookie = new CookieAccessor();
		int id_usuario = cookie.getCookieUsingCookieHandler();
				
		if(usuario.temAutorizacaoCadastro(id_usuario)) {
			String [] campos = webrequest.getParameterValues(usuario.listaDados().toString());
			
			if (usuario.cadastra(login, senha, pnome, unome, tipo, campos))
				saida = "yes";
			else
				saida = "no";
		}
		else {
			saida = "no_permit";
		}
		
		return saida;
	}
	
	@RequestMapping(value="remove_usuario", method=RequestMethod.GET)
	public String remove(@RequestParam("id_usuario") String id_usuario_apagar) {
		String saida = new String();
		
		CookieAccessor cookie = new CookieAccessor();
		int id_usuario = cookie.getCookieUsingCookieHandler();

		if(usuario.temAutorizacaoCadastro(id_usuario)) {
			if (usuario.remove(usuario.getUsuario(Integer.valueOf(id_usuario_apagar).intValue())))
				saida = "yes";
			else
				saida = "no";
		}
		else {
			saida = "no_permit";
		}
		
		return saida;
	}
	
	@RequestMapping(value="altera_usuario", method=RequestMethod.GET)
	public String altera(@RequestParam("id_usuario") String id_usuario_alterar, @RequestParam("login") String login, @RequestParam("senha") String senha, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, @RequestParam("tipo") String tipo, WebRequest webrequest) {
		String saida = new String();
		
		CookieAccessor cookie = new CookieAccessor();
		int id_usuario = cookie.getCookieUsingCookieHandler();

		if(usuario.temAutorizacaoCadastro(id_usuario)) {
			Usuario altera = usuario.getUsuario(Integer.valueOf(id_usuario_alterar).intValue());
			altera.setLogin(login);
			altera.setSenha(senha);
			altera.setPrimeiroNome(pnome);
			altera.setUltimoNome(unome);
			
			if (usuario.altera(altera))
				saida = "yes";
			else
				saida = "no";
		}
		else {
			saida = "no_permit";
		}
		
		return saida;
	}
	
	@RequestMapping(value="lista")
	public ModelAndView lista() {
		CookieAccessor cookie = new CookieAccessor();
		int id_usuario = cookie.getCookieUsingCookieHandler();
		System.out.println("id_usuario = "+id_usuario);
		
		if(usuario.temAutorizacaoListagem(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("usuario/lista");
			mav.addObject("usuarios", usuario.lista());
			
			mav.addObject("tipos", usuario.listaTipos());
			mav.addObject("campos", usuario.listaDados());
			mav.addObject("autorizacoes", usuario.listaAutorizacoes());
			
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
}
