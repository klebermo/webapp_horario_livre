package com.horariolivre.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.entity.Autorizacao;
import com.horariolivre.entity.Usuario;
import com.horariolivre.service.AtributoService;
import com.horariolivre.service.TipoService;
import com.horariolivre.service.UsuarioService;
import com.horariolivre.service.UsuarioService.json_list_auth;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuario;
	
	@Autowired
	private AtributoService atributo;
	
	@Autowired
	private TipoService tipo;
	
	@RequestMapping(value="cadastra")
	public ModelAndView cadastra(@ModelAttribute("username") String username) {
		int id_usuario = usuario.getUsuarioByUsername(username).getId();
		
		if(usuario.temAutorizacaoCadastro(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("usuario/cadastra");
			
			com.horariolivre.service.TipoService.json_list lista_tipos = tipo.getJsonList();
			lista_tipos.setLista(tipo.listaTipos());
			
			com.horariolivre.service.AtributoService.json_list lista_campos = atributo.getJsonList();
			lista_campos.setLista(atributo.listaCampos());
			
			mav.addObject("lista_tipos", lista_tipos.get());
			mav.addObject("lista_campos", lista_campos.get());
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
	
	@RequestMapping(value="cadastra_usuario", method=RequestMethod.POST)
	@ResponseBody
	public String cadastra_usuario(@ModelAttribute("username") String username, @RequestParam("login") String login, @RequestParam("senha1") String senha1, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, WebRequest webrequest) {
		System.out.println("cadastra_usuario");
		String saida = new String();
		
		int id_usuario = usuario.getUsuarioByUsername(username).getId();
				
		if(usuario.temAutorizacaoCadastro(id_usuario)) {
			System.out.println("temAutorizacao");
			
			String tipo = webrequest.getParameter("tipo");
			
			String[] key = atributo.listaKey();
			String[] value = new String[key.length];
			
			for(int i=0; i<key.length; i++)
				value[i] = webrequest.getParameter(key[i]);

			
			if (usuario.cadastra(login, senha1, pnome, unome, tipo, key, value)) {
				saida = "yes";
			}
			else {
				saida = "not";
			}
		}
		else {
			saida = "no_permit";
		}
		
		return saida;
	}
	
	@RequestMapping(value="remove_usuario", method=RequestMethod.GET)
	@ResponseBody
	public String remove_usuario(@ModelAttribute("username") String username, @RequestParam("id_usuario") String id_usuario_apagar) {
		String saida = new String();
		
		int id_usuario = usuario.getUsuarioByUsername(username).getId();

		if(usuario.temAutorizacaoCadastro(id_usuario)) {
			if (usuario.remove(usuario.getUsuarioById(Integer.valueOf(id_usuario_apagar).intValue())))
				saida = "yes";
			else
				saida = "not";
		}
		else {
			saida = "no_permit";
		}
		
		return saida;
	}
	
	@RequestMapping(value="altera_usuario", method=RequestMethod.POST)
	@ResponseBody
	public String altera_usuario(@ModelAttribute("username") String username, @RequestParam("id") String id_usuario_alterar, @RequestParam("login") String login, @RequestParam("senha") String senha, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, @RequestParam("tipo") String tipo, WebRequest webrequest) {
		String saida = new String();
		
		Usuario user = usuario.getUsuarioByUsername(username);

		if(usuario.temAutorizacaoCadastro(user.getId())) {
			Usuario altera = usuario.getUsuarioById(Integer.valueOf(id_usuario_alterar).intValue());
			
			String[] key = atributo.listaKey();
			String[] value = new String[key.length];
			
			for(int i=0; i<key.length; i++) {
				value[i] = webrequest.getParameter(key[i]);
			}
			
			if (usuario.altera(altera, senha, pnome, unome, tipo, key, value))
				saida = "yes";
			else
				saida = "not";
		}
		else {
			saida = "no_permit";
		}
		
		return saida;
	}
		
	@RequestMapping(value="lista")
	public ModelAndView lista(@ModelAttribute("username") String username) {
		Usuario user = usuario.getUsuarioByUsername(username);
		
		if(usuario.temAutorizacaoListagem(user.getId())) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("usuario/lista");
			mav.addObject("usuarios", usuario.lista());
			mav.addObject("tipos", tipo.listaTipos());
			mav.addObject("campos", atributo.listaKey());
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
	
	@RequestMapping(value="toggle_autorizacao", method=RequestMethod.GET)
	@ResponseBody
	public String toggle_autorizacao(@ModelAttribute("username") String username, @RequestParam("id_usuario") String id_usuario, @RequestParam("id_autorizacao") String id_autorizacao) {
		Usuario user = usuario.getUsuarioByUsername(username);
		
		if(usuario.temAutorizacaoPermissoes(user.getId())) {
			Usuario target = usuario.getUsuarioById(Integer.valueOf(id_usuario).intValue());
			Autorizacao cadastra = usuario.getAutorizacao(Integer.valueOf(id_autorizacao).intValue());
			if (usuario.toggle_autorizacao(target, cadastra))
				return "yes";
			else
				return "not";
		}
		return "no_permit";
	}
	
	@RequestMapping(value="lista_autorizacao", method=RequestMethod.GET)
	@ResponseBody
	public String lista_autorizacao() {
		json_list_auth lista = usuario.getJsonListAuth();
		lista.setLista(usuario.listaAutorizacoes());
		return lista.get();
	}
	
	@RequestMapping(value="lista_autorizacao_usuario", method=RequestMethod.GET)
	@ResponseBody
	public String lista_autorizacao(@RequestParam("id") String id_usuario) {
		json_list_auth lista = usuario.getJsonListAuth();
		lista.setLista(usuario.getUsuarioById(Integer.valueOf(id_usuario).intValue()).getAutorizacao());
		return lista.get();
	}
	
	@RequestMapping(value="perfil")
	public ModelAndView perfil(@ModelAttribute("username") String username) {
		Usuario user = usuario.getUsuarioByUsername(username);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/perfil");
		mav.addObject("usuario", user);
		
		com.horariolivre.service.TipoService.json_list lista_tipos = tipo.getJsonList();
		lista_tipos.setLista(tipo.listaTipos());
		
		com.horariolivre.service.AtributoService.json_list lista_campos = atributo.getJsonList();
		lista_campos.setLista(atributo.listaCampos(), atributo.listaValores(username));
		
		mav.addObject("lista_tipos", lista_tipos.get());
		mav.addObject("lista_campos", lista_campos.get());
		
		return mav;
	}
	
	@RequestMapping(value="salva_perfil", method=RequestMethod.POST)
	@ResponseBody
	public String salva_perfil(@ModelAttribute("username") String username, @RequestParam("senha1") String senha, @RequestParam("pnome") String pnome, @RequestParam("unome") String unome, @RequestParam("tipo") String tipo, WebRequest webrequest) {
		System.out.println("altera_usuario");
		String saida = new String();
		
		Usuario user = usuario.getUsuarioByUsername(username);
		
		String[] key = atributo.listaKey();
		String[] value = new String[key.length];
		
		for(int i=0; i<key.length; i++)
			value[i] = webrequest.getParameter(key[i]);

		
		if (usuario.altera(user, senha, pnome, unome, tipo, key, value)) {
			saida = "yes";
		}
		else {
			saida = "not";
		}
		
		return saida;
	}
		
	@RequestMapping(value="config")
	public ModelAndView config(@ModelAttribute("username") String username) {
		Usuario user = usuario.getUsuarioByUsername(username);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/acesso/config");
		mav.addObject("config", user.getConfig());
		
		return mav;
	}
	
	@RequestMapping(value="salvar_config", method=RequestMethod.POST)
	@ResponseBody
	public String salvar_config(@ModelAttribute("username") String username, @RequestParam("hora_inicial") String horaInicial, @RequestParam("hora_final") String horaFinal) {
		String saida = new String();
		
		if (usuario.salva_config(parseTime(horaInicial), parseTime(horaFinal), usuario.getUsuarioByUsername(username)))
			saida = "yes";
		else
			saida = "not";
		
		return saida;
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
