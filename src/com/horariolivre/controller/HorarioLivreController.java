package com.horariolivre.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.horariolivre.entity.HorarioLivre;
import com.horariolivre.entity.Usuario;
import com.horariolivre.service.EventoService;
import com.horariolivre.service.HorarioLivreService;
import com.horariolivre.service.HorarioLivreService.HorarioLivreInterno;
import com.horariolivre.service.UsuarioService;

@Controller
@SessionAttributes({"username"})
@RequestMapping(value="horario")
public class HorarioLivreController {
	
	@Autowired
	private HorarioLivreService horariolivre;
	
	@Autowired
	private EventoService evento;
	
	@Autowired
	private UsuarioService usuario;

	@RequestMapping(value="cadastra")
	public ModelAndView cadastra(@ModelAttribute("username") String username) {
		int id_usuario = horariolivre.getUsuarioByUsername(username).getId();
		
		if(horariolivre.temAutorizacaoCadastro(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("horario/cadastra");
			mav.addObject("lista_data", horariolivre.getListaData());
			mav.addObject("lista_hora", horariolivre.getListaHora(username));
			mav.addObject("lista_horarios_usuario", horariolivre.getListaHorarios(username));
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
	
	@RequestMapping(value="toggle_horario", method=RequestMethod.GET)
	@ResponseBody
	public String toggle_horario(@ModelAttribute("username") String username, @RequestParam("data") String data, @RequestParam("hora") String hora) {
		Date data2 = parseDate(data);
		Time hora2 = parseTime(hora);
		int id_usuario = horariolivre.getUsuarioByUsername(username).getId();
		
		if(horariolivre.existe(data2, hora2, username)) {
			if (horariolivre.remove(new HorarioLivre(data2, hora2, horariolivre.getUsuarioByUsername(username))))
				return "removed";
			else
				return "not removed";
		}
		else {
			if(horariolivre.cadastra(data2, hora2, id_usuario))
				return "added";
			else
				return "not added";
		}
	}
	
	@RequestMapping(value="lista")
	public ModelAndView lista(@ModelAttribute("username") String username) {
		int id_usuario = horariolivre.getUsuarioByUsername(username).getId();
		
		if(horariolivre.temAutorizacaoListagem(id_usuario)) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("horario/lista");
			mav.addObject("lista_eventos", evento.lista());
			mav.addObject("lista_usuarios", usuario.lista());
			return mav;
		}
		else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("erro/nao_autorizado");
			return mav;
		}
	}
	
	@RequestMapping(value="/find_horario", method=RequestMethod.GET)
	@ResponseBody
	public String lista_horario(@ModelAttribute("username") String username, @RequestParam("id_evento") String id_evento, @RequestParam("id_usuarios[]") String[] usuarios)
	{
		int id_usuario = horariolivre.getUsuarioByUsername(username).getId();
		Evento evento_em_uso = evento.getEvento(Integer.valueOf(id_evento).intValue());
		
		String saida = "Os horarios disponiveis para o evento são:<br/>";
		
		List<HorarioLivreInterno> lista_horario_evento = horariolivre.listaHorarioEvento(evento_em_uso);
		
		List<Usuario> lista_usuarios = new ArrayList<Usuario>();
		for(int i=0; i<usuarios.length; i++)
		{
			lista_usuarios.add(usuario.getUsuarioById(Integer.valueOf(usuarios[i])));
		}
		
		int array[] = new int[lista_horario_evento.size()];
		for(int i=0; i<lista_horario_evento.size(); i++)
			array[i] = 0;
		
			if(horariolivre.temAutorizacaoListagem(id_usuario))
			{
				for(int i=0; i<lista_horario_evento.size(); i++)
				{
					for(int j=0; j<lista_usuarios.size(); j++)
					{
						List<HorarioLivre> lista_horario_usuario = horariolivre.lista(lista_usuarios.get(j).getId());
						for(int k=0; k<lista_horario_usuario.size(); k++)
						{
							if(lista_horario_evento.get(i).equals(lista_horario_usuario.get(k)))
								array[i]++;
						}
					}
				}
				
				for(int i=0; i<lista_horario_evento.size(); i++)
				{
					if(array[i] == usuarios.length)
						saida = saida + "{" + lista_horario_evento.get(i).getData() + " " + lista_horario_evento.get(i).getHora() + "}<br/>";
				}
				
				return saida;
			}
			else
			{
				saida = "<div class=\"alert alert-danger\"><strong>Erro!</strong> Usu&aacute;rio não autorizado.</div>";
			}
		
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
		SimpleDateFormat format3 = new SimpleDateFormat("HH:mm:ss");  
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
