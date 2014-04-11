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
	
	public List<HorarioLivre> find_horario(List<HorarioLivre> lista_horario_evento, List<Usuario> lista_usuario) {
		List<HorarioLivre> lista = new ArrayList<HorarioLivre>();
		
		int array[] = new int[lista_horario_evento.size()];
		for(int i=0; i<lista_horario_evento.size(); i++)
			array[i] = 0;
		
		for(int i=0; i<lista_horario_evento.size(); i++) {
			for(int j=0; j<lista_usuario.size(); j++) {
				List<HorarioLivre> lista_horario_usuario = lista_usuario.get(j).getHorario_livre();
				for(int k=0; k<lista_horario_usuario.size(); k++) {
					if(lista_horario_evento.get(i).equals(lista_horario_usuario.get(k)))
						array[i]++;
				}
			}
		}
		
		for(int i=0; i<lista_horario_evento.size(); i++) {
			if(array[i] == lista_usuario.size())
				lista.add(lista_horario_evento.get(i));
		}
		
		return lista;
	}
	
	public void sequence(List<HorarioLivre> lista, Evento evento) {
		int sequenceLength = evento.getDuracao();
        int count = 0;
        HorarioLivre [] str1 = new HorarioLivre[sequenceLength];
        HorarioLivre [] str2 = new HorarioLivre[sequenceLength];

        for (int i = 0; i <= lista.size() - sequenceLength; i++) {
            for (int ii = i; ii <= lista.size() - sequenceLength; ii++) {
                
            	for(int index=0; index<sequenceLength; index++) {
            		str1[index].setData(lista.get(i+index).getData());
            		str1[index].setHora(lista.get(i+index).getHora());
            	}
                
            	for(int index=0; index<sequenceLength; index++) {
            		str2[index].setData(lista.get(ii+index).getData());
            		str2[index].setHora(lista.get(ii+index).getHora());
            	}
            	
            	int counter = 0;
            	for(int index=0; index<str1.length; index++) {
            		if(str1[index].equals(str2[index]))
            			counter++;
            	}

                if (counter == str1.length && i != ii) {
                    System.out.println(str1[0].toString());
                    count++;
                }
                
                System.out.println("count="+count);
            }
        }
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
		List<HorarioLivre> lista = owner.getHorario_livre();
		if(lista == null) {
			lista = new ArrayList<HorarioLivre>();
			lista.add(new HorarioLivre(new Date(0), new Time(0)));
		}
		if(lista.size() == 0)
			lista.add(new HorarioLivre(new Date(0), new Time(0)));
		return lista;
	}
	
	public Usuario getUsuarioById(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	public Usuario getUsuarioByUsername(String username) {
		return usuario.findByUsername(username);
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
	
	public List<HorarioLivre> listaHorarioEvento(Evento evento) {
		List<HorarioLivre> lista = new ArrayList<HorarioLivre>();
		
		Calendar start = Calendar.getInstance();
		start.setTime(evento.getDataInicial());
		
		Calendar end = Calendar.getInstance();
		end.setTime(evento.getDataFinal());
		
		for(Calendar i=start; i.before(end); i.add(Calendar.DATE, 1)) {
			Calendar hora_0 = Calendar.getInstance();
			hora_0.setTimeInMillis(evento.getHoraInicial().getTime());
			
			Calendar hora_1 = Calendar.getInstance();
			hora_1.setTimeInMillis(evento.getHoraFinal().getTime());
			
			for(Calendar j=hora_0; j.before(hora_1); j.add(Calendar.MINUTE, 1)) {
				Date data= new Date(i.getTimeInMillis());
				Time hora = new Time(j.getTimeInMillis());
				lista.add(new HorarioLivre(data, hora));
			}
		}
		
		return lista;
	}
	
	public List<HorarioLivre> listaHorarioUsuario(Usuario user) {
		List<HorarioLivre> lista = new ArrayList<HorarioLivre>();
		List<HorarioLivre> lista_horario_usuario = this.lista(user.getId());
		
		for(int i=0; i<lista_horario_usuario.size(); i++) {
			Date data = lista_horario_usuario.get(i).getData();
			
			Calendar start = Calendar.getInstance();
			start.setTimeInMillis(lista_horario_usuario.get(i).getHora().getTime());
			
			Calendar end = Calendar.getInstance();
			end.setTimeInMillis(lista_horario_usuario.get(i).getHora().getTime());
			end.add(Calendar.MINUTE, intervalo);
			
			for(Calendar j=start; j.before(end); j.add(Calendar.MINUTE, 1)) {
				Time novo = new Time(j.getTimeInMillis());
				lista.add(new HorarioLivre(data, novo));
			}
		}
		
		return lista;
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
	
	public json_list_data getListaJsonData() {
		return new json_list_data();
	}
	
	public json_list_hora getListaJsonHora() {
		return new json_list_hora();
	}
	
	public json_list_horario getListaJsonHorario() {
		return new json_list_horario();
	}
	
	public class json_node_data {
		private Date data;

		public Date getData() {
			return data;
		}

		public void setData(Date data) {
			this.data = data;
		}
		
		public String get() {
			String node = new String(), nova_data = new String();
			
			String [] meses = {"", "JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
			Calendar temp = Calendar.getInstance();
			temp.setTimeInMillis(this.data.getTime());
			int dia = temp.get(Calendar.DATE);
			int mes = temp.get(Calendar.MONDAY);
			nova_data = String.valueOf(dia)+"/"+meses[mes];
			
			node = "\"" + "data" + "\"" + ":" + "\"" + data.toString() + "\"" + "," + "\"" + "string" + "\"" + ":" + "\"" + nova_data + "\"";
			return node;
		}
	}
	
	public class json_list_data {
		private List<json_node_data> lista = new ArrayList<json_node_data>();

		public List<json_node_data> getLista() {
			return lista;
		}

		public void setLista(List<json_node_data> lista) {
			this.lista = lista;
		}
		
		public void add(Date data) {
			json_node_data node = new json_node_data();
			node.setData(data);
			lista.add(node);
		}
		
		public String get() {
			int i, max = lista.size();
			String json = "{\"Data\":[";
			for(i=0; i<max-1; i++) {
				json = json + "{";
				json_node_data temp = new json_node_data();
				System.out.println(lista.get(i).getData().toString());
				temp.setData(lista.get(i).getData());
				json = json + temp.get() + "},";
			}
			json_node_data temp = new json_node_data();
			temp.setData(lista.get(i).getData());
			json = json + "{" + temp.get() + "}]}";
			return json;
		}
		
		public void set(List<Date> lista) {
			for(int i=0; i<lista.size(); i++) {
				json_node_data node = new json_node_data();
				node.setData(lista.get(i));
				this.lista.add(node);
			}
		}
	}
	
	public class json_node_hora {
		private Time hora;

		public String get() {
			String node = new String(), nova_hora = new String();
			
			Calendar temp1 = Calendar.getInstance();
			temp1.setTimeInMillis(hora.getTime());
			int hour = temp1.get(Calendar.HOUR_OF_DAY);
			int minute = temp1.get(Calendar.MINUTE);
			nova_hora = String.valueOf(hour)+":"+String.valueOf(minute);
			
			Calendar temp2 = Calendar.getInstance();
			temp2.setTimeInMillis(hora.getTime());
			temp2.add(Calendar.MINUTE, intervalo);
			hour = temp1.get(Calendar.HOUR_OF_DAY);
			minute = temp1.get(Calendar.MINUTE);
			nova_hora += "-"+String.valueOf(hour)+":"+String.valueOf(minute);
			
			node = "\"" + "hora" + "\"" + ":" + "\"" + hora.toString() + "\"" + "," + "\"" + "string" + "\"" + ":" + "\"" + nova_hora + "\"";
			return node;
		}

		public Time getHora() {
			return hora;
		}

		public void setHora(Time hora) {
			this.hora = hora;
		}
	}
	
	public class json_list_hora {
		private List<json_node_hora> lista = new ArrayList<json_node_hora>();

		public List<json_node_hora> getLista() {
			return lista;
		}

		public void setLista(List<json_node_hora> lista) {
			this.lista = lista;
		}
		
		public void add(Time hora) {
			json_node_hora node = new json_node_hora();
			node.setHora(hora);
			lista.add(node);
		}
		
		public String get() {
			int i, max = lista.size();
			String json = "{\"Hora\":[";
			for(i=0; i<max-1; i++) {
				json = json + "{";
				json_node_hora temp = new json_node_hora();
				System.out.println(lista.get(i).getHora().toString());
				temp.setHora(lista.get(i).getHora());
				json = json + temp.get() + "},";
			}
			json_node_hora temp = new json_node_hora();
			temp.setHora(lista.get(i).getHora());
			json = json + "{" + temp.get() + "}]}";
			return json;
		}
		
		public void set(List<Time> lista) {
			for(int i=0; i<lista.size(); i++) {
				json_node_hora node = new json_node_hora();
				node.setHora(lista.get(i));
				this.lista.add(node);
			}
		}
	}
	
	public class json_node_horario {
		private HorarioLivre horario;

		public String get() {
			String node = new String(), nova_data = new String(), nova_hora = new String();
			
			if(horario != null) {
				String [] meses = {"", "JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
				Calendar temp = Calendar.getInstance();
				temp.setTimeInMillis(this.horario.getData().getTime());
				int dia = temp.get(Calendar.DATE);
				int mes = temp.get(Calendar.MONDAY);
				nova_data = String.valueOf(dia)+"/"+meses[mes];
				
				Calendar temp1 = Calendar.getInstance();
				temp1.setTimeInMillis(horario.getHora().getTime());
				int hour = temp1.get(Calendar.HOUR_OF_DAY);
				int minute = temp1.get(Calendar.MINUTE);
				nova_hora = String.valueOf(hour)+":"+String.valueOf(minute);
				
				Calendar temp2 = Calendar.getInstance();
				temp2.setTimeInMillis(horario.getHora().getTime());
				temp2.add(Calendar.MINUTE, intervalo);
				hour = temp1.get(Calendar.HOUR_OF_DAY);
				minute = temp1.get(Calendar.MINUTE);
				nova_hora += "-"+String.valueOf(hour)+":"+String.valueOf(minute);
				
				node = "\"" + "horario" + "\"" + ":" + "\"" + horario.toString() + "\"" + ",";
				node = node + "\"" +  "data" + "\"" + ":" + "\"" + horario.getData().toString() + "\"" + ",";
				node = node + "\"" + "string_data" + "\"" + ":" + "\"" + nova_data + "\"" + ",";
				node = node + "\"" + "hora" + "\"" + ":" + "\"" + horario.getHora().toString() + "\""  + ",";
				node = node + "\"" + "string_hora" + "\"" + ":" + "\"" + nova_hora + "\"";
			}
			else {
				node = "\"" + "horario" + "\"" + ":" + "\"" + "-1" + "\"";
			}
			
			return node;
		}

		public HorarioLivre getHorario() {
			return horario;
		}

		public void setHorario(HorarioLivre horario) {
			this.horario = horario;
		}

	}
	
	public class json_list_horario {
		private List<json_node_horario> lista = new ArrayList<json_node_horario>();

		public List<json_node_horario> getLista() {
			return lista;
		}

		public void setLista(List<json_node_horario> lista) {
			this.lista = lista;
		}
		
		public void add(HorarioLivre horario) {
			json_node_horario node = new json_node_horario();
			node.setHorario(horario);
			lista.add(node);
		}
		
		public String get() {
			int i, max = lista.size();
			if(max > 0) {
				String json = "{\"Horario\":[";
				for(i=0; i<max-1; i++) {
					json = json + "{";
					json_node_horario temp = new json_node_horario();
					System.out.println(lista.get(i).getHorario().toString());
					temp.setHorario(lista.get(i).getHorario());
					json = json + temp.get() + "},";
				}
				json_node_horario temp = new json_node_horario();
				temp.setHorario(lista.get(i).getHorario());
				json = json + "{" + temp.get() + "}]}";
				return json;
			}
			else {
				String json = "{\"Horario\":[";
				json_node_horario temp = new json_node_horario();
				temp.setHorario(null);
				json = json + "{" + temp.get() + "}]}";
				return json;
			}
		}
		
		public void set(List<HorarioLivre> lista) {
			for(int i=0; i<lista.size(); i++) {
				json_node_horario node = new json_node_horario();
				node.setHorario(lista.get(i));
				this.lista.add(node);
			}
		}
	}
	
}
