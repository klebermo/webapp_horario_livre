package com.horariolivre.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.horariolivre.dao.TipoHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Tipo;
import com.horariolivre.entity.Usuario;

@Service
public class TipoService {
	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private TipoHome tipo;
	
	@Transactional
	public boolean cadastra(String tipoUsuario) {
		return tipo.persist(new Tipo(tipoUsuario));
	}
	
	@Transactional
	public boolean remover(String tipoUsuario) {
		return tipo.remove(this.tipo.findById(Integer.valueOf(tipoUsuario).intValue()));
	}
	
	@Transactional
	public Tipo getTipo(String tipoUsuario) {
		return tipo.findByNome(tipoUsuario);
	}
	
	@Transactional
	public List<Tipo> listaTipos() {
		return tipo.findALL();
	}
	
	@Transactional
	public Usuario getUsuarioById(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	@Transactional
	public Usuario getUsuarioByUsername(String username) {
		return usuario.findByUsername(username);
	}
	
	public boolean temAutorizacao(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacao().size(); i++) {
			if(novo.getAutorizacao().get(i).getNome().equals("cad_tipo"))
				return true;
		}
		
		return false;
	}
	
	public json_list getJsonList() {
		json_list lista = new json_list();
		return lista;
	}
	
	public class json_node {
		private Tipo tipo;

		public Tipo getTipo() {
			return tipo;
		}

		public void setTipo(Tipo tipo) {
			this.tipo = tipo;
		}
		
		public String get() {
			String node = new String();
			
			if(tipo == null)
				node = "\"" + "id" + "\"" + ":" + "-1";
			else
				node = "\"" + "id" + "\"" + ":" + tipo.getId() + "," + "\"" + "nome" + "\"" + ":" + "\"" + tipo.getNome() + "\"";
			
			return node;
		}
		
		public void set(Tipo item) {
			this.setTipo(item);
		}
		
		public json_node() {
			this.tipo = new Tipo();
		}
	}
	
	public class json_list {
		private List<json_node> lista;

		public List<json_node> getLista() {
			return lista;
		}

		public void setLista(List<Tipo> lista) {
			int max = lista.size();
			
			for(int i=0; i<max; i++) {
				json_node e = new json_node();
				e.set(lista.get(i));
				this.lista.add(e);
			}
		}
		
		public String get() {
			int i, max = lista.size();
			if (max == 0) {
				String json = "{\"Tipo\":[";
				json_node node = new json_node();
				json = json + "{" + node + "}]}";
				return json;
			}
			String json = "{\"Tipo\":[";
			for(i=0; i<max-1; i++) {
				json = json + "{";
				json_node temp = new json_node();
				temp.setTipo(lista.get(i).getTipo());
				json = json + temp.get() + "},";
			}
			json_node temp = new json_node();
			temp.setTipo(lista.get(i).getTipo());
			json = json + "{" + temp.get() + "}]}";
			return json;
		}
		
		public void set(Tipo item) {
			json_node aux = new json_node();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public json_list() {
			this.lista = new ArrayList<json_node>();
		}
	}
}
