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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.horariolivre.entity.Evento;
import com.horariolivre.service.EventoService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="evento")
public class EventoController {
	@Autowired
	private EventoService evento;
	
	@RequestMapping(value="cadastra")
	public ModelAndView cadastra(@ModelAttribute("username") String username) {
		int id_usuario = evento.getUsuarioByUsername(username).getId();
		
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
	
	@RequestMapping(value="cadastra_evento", method=RequestMethod.POST)
	@ResponseBody
	public String cadastra_evento(@ModelAttribute("username") String username, @RequestParam("nome") String nome, @RequestParam("descricao") String descricao, @RequestParam("data_inicial") String dataInicial, @RequestParam("data_final") String dataFinal, @RequestParam("hora_inicial") String horaInicial, @RequestParam("hora_final") String horaFinal, @RequestParam("duracao") String duracao) {
		String saida = new String();
		
		int id_usuario = evento.getUsuarioByUsername(username).getId();
		
		Date d_inicial = parseDate(dataInicial);
		Date d_final = parseDate(dataFinal);
		Time h_inicial = parseTime(horaInicial);
		Time h_final = parseTime(horaFinal);
		

		if (evento.cadastra(id_usuario, nome, descricao, d_inicial, d_final, h_inicial, h_final, Integer.parseInt(duracao)))
			saida = "yes";
		else
			saida = "not";
		
		return saida;
	}
	
	@RequestMapping(value="remove_evento", method=RequestMethod.GET)
	@ResponseBody
	public String remove_evento(@RequestParam("id") String id_evento_apagar) {
		String saida = new String();
		int id_evento = Integer.valueOf(id_evento_apagar).intValue();
		
		if(evento.remove(evento.getEvento(id_evento))) {
			saida = "yes";
		}
		else {
			saida = "not";
		}
		return saida;
	}
	
	@RequestMapping(value="altera_evento", method=RequestMethod.POST)
	@ResponseBody
	public String altera_evento(@RequestParam("id") String id_evento_alterar, @RequestParam("nome") String nome, @RequestParam("descricao") String descricao, @RequestParam("data_inicial") Date dataInicial, @RequestParam("data_final") Date dataFinal, @RequestParam("hora_inicial") Time horaInicial, @RequestParam("hora_final") Time horaFinal, @RequestParam("duracao") String duracao) {
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
			saida = "not";
		}
		return saida;
	}
	
	@RequestMapping(value="lista")
	public ModelAndView lista(@ModelAttribute("username") String username) {
		int id_usuario = evento.getUsuarioByUsername(username).getId();
		
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
	
	private Date parseDate(String data) {
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");  
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
