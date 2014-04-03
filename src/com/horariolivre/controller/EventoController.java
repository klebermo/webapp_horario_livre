package com.horariolivre.controller;

import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Evento;
import com.horariolivre.service.EventoService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="evento")
public class EventoController {
	@Autowired
	private EventoService evento;
	
	@Autowired
	private UsuarioHome usuario;
	
	@RequestMapping(value="cadastra")
	public ModelAndView cadastra(@ModelAttribute("username") String username) {
		int id_usuario = usuario.findByUsername(username).getId();
		
		if(evento.temAutorizacaoCadastro(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("evento/cadastra");
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
	
	@RequestMapping(value="cadastra_evento", method=RequestMethod.GET)
	public String cadastra_evento(@ModelAttribute("username") String username, @RequestParam("nome") String nome, @RequestParam("descricao") String descricao, @RequestParam("data_inicial") Date dataInicial, @RequestParam("data_final") Date dataFinal, @RequestParam("hora_inicial") Time horaInicial, @RequestParam("hora_final") Time horaFinal, @RequestParam("duracao") String duracao) {
		String saida = new String();
		
		int id_usuario = usuario.findByUsername(username).getId();

		if (evento.cadastra(id_usuario, nome, descricao, dataInicial, dataFinal, horaInicial, horaFinal, Integer.parseInt(duracao)))
			saida = "yes";
		else
			saida = "no";
		
		return saida;
	}
	
	@RequestMapping(value="remove_evento", method=RequestMethod.GET)
	public String remove_evento(@RequestParam("id_evento") String id_evento_apagar) {
		String saida = new String();
		int id_evento = Integer.valueOf(id_evento_apagar).intValue();
		
		if(evento.remove(evento.getEvento(id_evento))) {
			saida = "yes";
		}
		else {
			saida = "no";
		}
		return saida;
	}
	
	@RequestMapping(value="altera_evento", method=RequestMethod.GET)
	public String altera_evento(@RequestParam("id_evento") String id_evento_alterar, @RequestParam("nome") String nome, @RequestParam("descricao") String descricao, @RequestParam("data_inicial") Date dataInicial, @RequestParam("data_final") Date dataFinal, @RequestParam("hora_inicial") Time horaInicial, @RequestParam("hora_final") Time horaFinal, @RequestParam("duracao") String duracao) {
		String saida = new String();
		
		Evento altera = evento.getEvento(Integer.valueOf(id_evento_alterar).intValue());
		altera.setNome(nome);
		altera.setDescricao(descricao);
		altera.setDataInicial(dataInicial);
		altera.setDataFinal(dataFinal);
		altera.setHoraInicial(horaInicial);
		altera.setHoraFinal(horaFinal);
		altera.setDuracao(Integer.valueOf(duracao).intValue());
		
		if(evento.altera(altera)) {
			saida = "yes";
		}
		else {
			saida = "no";
		}
		return saida;
	}
	
	@RequestMapping(value="lista")
	public ModelAndView lista(@ModelAttribute("username") String username) {
		int id_usuario = usuario.findByUsername(username).getId();
		
		if(evento.temAutorizacaoListagem(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("evento/lista");
			mav.addObject("lista", evento.lista());
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
}
