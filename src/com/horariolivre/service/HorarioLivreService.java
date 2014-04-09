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
		HorarioLivre horario = new HorarioLivre(data, hora);
		horariolivre.persist(horario);
		owner.getHorario_livre().add(horario);
		if(usuario.merge(owner) != null)
			return true;
		else
			return false;
	}
	
	public boolean remove(HorarioLivre horario) {
		return horariolivre.remove(horario);
	}
	
	public boolean existe(Date data, Time hora, String username) {
		Usuario owner = usuario.findByUsername(username);
		List<HorarioLivre> lista = owner.getHorario_livre();
		int max = lista.size();
		for(int i=0; i<max; i++) {
			if(lista.get(i).getData().equals(data) && lista.get(i).getHora().equals(hora)) {
				return true;
			}
		}
		return false;
	}
	public List<HorarioLivre> lista(int id_usuario) {
		Usuario owner = usuario.findById(id_usuario);
		return owner.getHorario_livre();
	}
	
	public Usuario getUsuarioById(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	public Usuario getUsuarioByUsername(String username) {
		return usuario.findByUsername(username);
	}
	
	public List<HorarioLivreInterno> listaHorarioUsuario(Usuario user) {
		List<HorarioLivreInterno> lista = new ArrayList<HorarioLivreInterno>();
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, 10);
		
		for(Calendar i=start; i.before(end); i.add(Calendar.DATE, 1)) {
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
	
	public List<HorarioLivreString> getlistaHorarioUsuario(Usuario user) {
		List<HorarioLivreString> lista = new ArrayList<HorarioLivreString>();
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, 10);
		
		for(Calendar i=start; i.before(end); i.add(Calendar.DATE, 1)) {
			Calendar inicio = Calendar.getInstance();
			inicio.setTimeInMillis(user.getConfig().getHoraInicial().getTime());
			
			Calendar fim = Calendar.getInstance();
			fim.setTimeInMillis(user.getConfig().getHoraFinal().getTime());
			
			for(Calendar j=inicio; j.before(fim); j.add(Calendar.MINUTE, intervalo)) {
				Date d = new Date(i.getTimeInMillis());
				Time t = new Time(j.getTimeInMillis());
				lista.add(new HorarioLivreString(d, t));
			}
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
				Date d = new Date(i.getTimeInMillis());
				Time t = new Time(j.getTimeInMillis());
				lista.add(new HorarioLivreInterno(d, t));
			}
		}
		
		return lista;
	}
	
	public List<HorarioLivreString> getlistaHorarioEvento(Evento evento_interno) {
		List<HorarioLivreString> lista = new ArrayList<HorarioLivreString>();
		
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
				Date d = new Date(i.getTimeInMillis());
				Time t = new Time(j.getTimeInMillis());
				lista.add(new HorarioLivreString(d, t));
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
	
	public List<String> listaData() {
		String [] meses = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
		List<String> lista = new ArrayList<String>();
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.add(Calendar.DATE, 10);
		
		for(Calendar i=start; i.before(end); i.add(Calendar.DATE, 1)) {
			int d = i.get(Calendar.DATE);
			int m = i.get(Calendar.MONTH);
			if(d<10) {
				if(m<10) {
					lista.add("0"+String.valueOf(d)+" "+meses[m]);
				}
				else {
					lista.add("0"+String.valueOf(d)+" "+meses[m]);
				}
			}
			else {
				if(m<10) {
					lista.add(String.valueOf(d)+" "+meses[m]);
				}
				else {
					lista.add(String.valueOf(d)+" "+meses[m]);
				}
			}
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
	
	public List<String> listaHora(String username) {
		Usuario user = this.getUsuarioByUsername(username);
		List<String> lista = new ArrayList<String>();
		
		Calendar h1 = Calendar.getInstance();
		h1.setTimeInMillis(user.getConfig().getHoraInicial().getTime());
		
		Calendar h2 = Calendar.getInstance();
		h2.setTimeInMillis(user.getConfig().getHoraFinal().getTime());
		
		Calendar i = h1;
		do {
			int hora1 = i.get(Calendar.HOUR_OF_DAY);
			int minuto1 = i.get(Calendar.MINUTE);
			
			i.add(Calendar.MINUTE, intervalo);
			
			int hora2 = i.get(Calendar.HOUR_OF_DAY);
			int minuto2 = i.get(Calendar.MINUTE);
			
			if(minuto1<10) {
				if(minuto2<10) {
					lista.add(String.valueOf(hora1)+":"+"0"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+"0"+String.valueOf(minuto2));
				}
				else {
					lista.add(String.valueOf(hora1)+":"+"0"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+String.valueOf(minuto2));
				}
			}
			else {
				if(minuto2<10) {
					lista.add(String.valueOf(hora1)+":"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+"0"+String.valueOf(minuto2));
				}
				else {
					lista.add(String.valueOf(hora1)+":"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+String.valueOf(minuto2));
				}
			}
		}while(i.before(h2));
		
		return lista;
	}
	
	public class HorarioLivreString {
		private Date data;
		private Time hora;
		public HorarioLivreString(Date date, Time time) {
			this.setData(date);
			this.setHora(time);
		}
		public String getData() {
			String [] meses = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
			
			Calendar data = Calendar.getInstance();
			data.setTimeInMillis(this.data.getTime());
			
			int dia = data.get(Calendar.DATE);
			int mes = data.get(Calendar.MONTH);
			
			return String.valueOf(dia)+" "+meses[mes];
		}
		public void setData(Date data) {
			this.data = data;
		}
		public String getHora() {
			Calendar h1 = Calendar.getInstance();
			h1.setTimeInMillis(this.hora.getTime());
			
			int hora1 = h1.get(Calendar.HOUR_OF_DAY);
			int minuto1 = h1.get(Calendar.MINUTE);
			
			Calendar h2 = Calendar.getInstance();
			h2.setTimeInMillis(this.hora.getTime());
			h2.add(Calendar.MINUTE, intervalo);
			
			int hora2 = h2.get(Calendar.HOUR_OF_DAY);
			int minuto2 = h2.get(Calendar.MINUTE);
			
			if(minuto1<10) {
				if(minuto2<10) {
					return String.valueOf(hora1)+":"+"0"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+"0"+String.valueOf(minuto2);
				}
				else {
					return String.valueOf(hora1)+":"+"0"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+String.valueOf(minuto2);
				}
			}
			else {
				if(minuto2<10) {
					return String.valueOf(hora1)+":"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+"0"+String.valueOf(minuto2);
				}
				else {
					return String.valueOf(hora1)+":"+String.valueOf(minuto1)+"-"+String.valueOf(hora2)+":"+String.valueOf(minuto2);
				}
			}
		}
		public void setHora(Time hora) {
			this.hora = hora;
		}
		public boolean equals(HorarioLivre horario) {
			return this.data.equals(horario.getData()) && this.hora.equals(horario.getHora());
		}
	}
	
	public class HorarioLivreInterno {
		private Date data;
		private Time hora;
		public HorarioLivreInterno(Date date, Time time) {
			this.setData(date);
			this.setHora(time);
		}
		public Date getData() {
			return data;
		}
		public void setData(Date data) {
			this.data = data;
		}
		public Time getHora() {
			return hora;
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
