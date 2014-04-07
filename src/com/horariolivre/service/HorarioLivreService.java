package com.horariolivre.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.horariolivre.dao.HorarioLivreHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Evento;
import com.horariolivre.entity.HorarioLivre;
import com.horariolivre.entity.Usuario;

@Component
public class HorarioLivreService {
	
	private int intervalo = 30;
	
	@Autowired
	private HorarioLivreHome horariolivre;
	
	@Autowired
	private UsuarioHome usuario;
	
	public boolean cadastra(Date data, Time hora, int id_usuario) {
		Usuario owner = usuario.findById(id_usuario);
		HorarioLivre horario = new HorarioLivre(data, hora, owner);
		return horariolivre.persist(horario);
	}
	
	public boolean remove(HorarioLivre horario) {
		return horariolivre.remove(horario);
	}
	
	public boolean existe(Date data, Time hora, String username) {
		if(horariolivre.findByExample(new HorarioLivre(data, hora, this.getUsuarioByUsername(username))).size() > 0)
			return true;
		else
			return false;
	}
	public List<HorarioLivre> lista(int id_usuario) {
		return horariolivre.findByUsuario(usuario.findById(id_usuario));
	}
	
	public Usuario getUsuarioById(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	public Usuario getUsuarioByUsername(String username) {
		return usuario.findByUsername(username);
	}
	
	public List<HorarioLivreInterno> getListaHorarios(String username) {
		List<HorarioLivreInterno> lista = new ArrayList<HorarioLivreInterno>();
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, 10);
		
		for(Calendar i=start; i.before(end); i.add(Calendar.DATE, 1)) {
			Usuario user = this.getUsuarioByUsername(username);
			
			Calendar inicio = Calendar.getInstance();
			inicio.setTimeInMillis(user.getConfig().getHoraInicial().getTime());
			
			Calendar fim = Calendar.getInstance();
			fim.setTimeInMillis(user.getConfig().getHoraFinal().getTime());
			
			for(Calendar j=inicio; j.before(fim); j.add(Calendar.MINUTE, intervalo)) {
				Date d = new Date(i.getTimeInMillis());
				Time t = new Time(j.getTimeInMillis());
				lista.add(new HorarioLivreInterno(d, t));
			}
		}
		
		return lista;
	}
	
	public List<Date> getListaData() {
		List<Date> lista = new ArrayList<Date>();
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, 10);
		
		for(Calendar i=start; i.before(end); i.add(Calendar.DATE, 1)) {
			lista.add(new Date(i.getTimeInMillis()));
		}
		
		return lista;
	}
	
	public List<Time> getListaHora(String username) {
		Usuario user = this.getUsuarioByUsername(username);
		List<Time> lista = new ArrayList<Time>();
		
		Calendar inicio = Calendar.getInstance();
		inicio.setTimeInMillis(user.getConfig().getHoraInicial().getTime());
		
		Calendar fim = Calendar.getInstance();
		fim.setTimeInMillis(user.getConfig().getHoraFinal().getTime());
		
		for(Calendar i=inicio; i.before(fim); i.add(Calendar.MINUTE, intervalo)) {
			lista.add(new Time(i.getTimeInMillis()));
		}
		
		return lista;
	}
		
	public List<HorarioLivreInterno> listaHorarioEvento(Evento evento_interno) {
		List<HorarioLivreInterno> lista = new ArrayList<HorarioLivreInterno>();
		
		Calendar inicio = Calendar.getInstance();
		inicio.setTime(evento_interno.getDataInicial());
		
		Calendar fim = Calendar.getInstance();
		fim.setTime(evento_interno.getDataFinal());
		
		for(Calendar i=inicio; i.before(fim); i.add(Calendar.DATE, 1)) {
			Calendar hora_inicial = Calendar.getInstance();
			hora_inicial.setTimeInMillis(evento_interno.getHoraInicial().getTime());
			
			Calendar hora_final = Calendar.getInstance();
			hora_final.setTimeInMillis(evento_interno.getHoraFinal().getTime());
			
			for(Calendar j=hora_inicial; j.before(hora_final); j.add(Calendar.MINUTE, 1)) {
				lista.add(new HorarioLivreInterno(new Date(i.getTimeInMillis()), new Time(j.getTimeInMillis())));
			}
		}
		
		return lista;
	}
	
	public class HorarioLivreInterno {
		private Date data;
		private Time hora;
		public HorarioLivreInterno(Date date, Time time) {
			this.setData(date);
			this.setHora(time);
		}
		public Date getDate() {
			return data;
		}
		public String getData() {
			Calendar data = Calendar.getInstance();
			data.setTimeInMillis(this.data.getTime());
			
			int dia = data.get(Calendar.DATE);
			int mes = data.get(Calendar.MONTH);
			
			return String.valueOf(dia)+"/"+String.valueOf(mes);
		}
		public void setData(Date data) {
			this.data = data;
		}
		public Time getTime() {
			return hora;
		}
		public String getHora() {
			Calendar h1 = Calendar.getInstance();
			h1.setTimeInMillis(this.hora.getTime());
			
			int hora = h1.get(Calendar.HOUR_OF_DAY);
			int minuto = h1.get(Calendar.MINUTE);
			
			Calendar h2 = Calendar.getInstance();
			h2.setTimeInMillis(this.hora.getTime());
			h2.add(Calendar.MINUTE, intervalo);
			
			int hora2 = h2.get(Calendar.HOUR_OF_DAY);
			int minuto2 = h2.get(Calendar.MINUTE);
			
			return String.valueOf(hora)+":"+String.valueOf(minuto)+"-"+String.valueOf(hora2)+":"+String.valueOf(minuto2);
		}
		public void setHora(Time hora) {
			this.hora = hora;
		}
		public boolean equals(HorarioLivre horario) {
			 return this.getData().equals(horario.getData()) && this.getHora().equals(horario.getHora());
		}
	}
	
	public boolean temAutorizacaoCadastro(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacao().size(); i++) {
			if(novo.getAutorizacao().get(i).getNome().equals("cad_horario"))
				return true;
		}
		
		return false;
	}
	
	public boolean temAutorizacaoListagem(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacao().size(); i++) {
			if(novo.getAutorizacao().get(i).getNome().equals("lista_horario"))
				return true;
		}
		
		return false;
	}
	
}
