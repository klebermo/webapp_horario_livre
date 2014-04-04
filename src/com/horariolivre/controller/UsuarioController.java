package com.horariolivre.controller;

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
@RequestMapping(value="usuario")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioHome usuario;
	
	@RequestMapping(value="cadastra")
	public ModelAndView cadastra(@ModelAttribute("username") String username) {
		int id_usuario = usuario.findByUsername(username).getId();
		
		if(usuarioService.temAutorizacaoCadastro(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("usuario/cadastra");
			mav.addObject("tipos", usuarioService.listaTipos());
			mav.addObject("campos", usuarioService.listaKey());
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
	
	@RequestMapping(value="cadastra_usuario", method=RequestMethod.POST)
	public String cadastra(@ModelAttribute("username") String username, @RequestParam("login") String login, @RequestParam("senha1") String senha1, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, @RequestParam("tipo") String tipo, WebRequest webrequest) {
		String saida = new String();
		
		int id_usuario = usuario.findByUsername(username).getId();
				
		if(usuarioService.temAutorizacaoCadastro(id_usuario)) {
			String[] key = usuarioService.listaKey();
			String[] value = new String[key.length];
			
			for(int i=0; i<key.length; i++) {
				value[i] = webrequest.getParameter(key[i]);
			}
			
			if (usuarioService.cadastra(login, senha1, pnome, unome, new Tipo(tipo), key, value))
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
	public String remove(@ModelAttribute("username") String username, @RequestParam("id_usuario") String id_usuario_apagar) {
		String saida = new String();
		
		int id_usuario = usuario.findByUsername(username).getId();

		if(usuarioService.temAutorizacaoCadastro(id_usuario)) {
			if (usuarioService.remove(usuarioService.getUsuario(Integer.valueOf(id_usuario_apagar).intValue())))
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
	public String altera(@ModelAttribute("username") String username, @RequestParam("id_usuario") String id_usuario_alterar, @RequestParam("login") String login, @RequestParam("senha") String senha, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, @RequestParam("tipo") String tipo, WebRequest webrequest) {
		String saida = new String();
		
		int id_usuario = usuario.findByUsername(username).getId();

		if(usuarioService.temAutorizacaoCadastro(id_usuario)) {
			Usuario altera = usuarioService.getUsuario(Integer.valueOf(id_usuario_alterar).intValue());
			altera.setLogin(login);
			altera.setSenha(senha);
			altera.setPrimeiroNome(pnome);
			altera.setUltimoNome(unome);
			
			if (usuarioService.altera(altera))
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
	public ModelAndView lista(@ModelAttribute("username") String username) {
		int id_usuario = usuario.findByUsername(username).getId();
		
		if(usuarioService.temAutorizacaoListagem(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("usuario/lista");
			mav.addObject("usuarios", usuarioService.lista());
			mav.addObject("autorizacao", usuarioService.listaAutorizacoes());
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
}
