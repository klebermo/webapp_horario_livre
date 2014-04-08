package com.horariolivre.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AtributoHome;
import com.horariolivre.dao.KeyHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.dao.ValueHome;
import com.horariolivre.entity.Atributo;
import com.horariolivre.entity.Key;
import com.horariolivre.entity.Usuario;
import com.horariolivre.entity.Value;

@Service
public class AtributoService {
	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private AtributoHome atributo;
	
	@Autowired
	private KeyHome key;
	
	@Autowired
	private ValueHome value;
	
	public boolean cadastra(String campo) {
		return key.persist(new Key(campo));
	}
	
	public boolean remover(String campo) {
		return key.remove(key.findByNome(campo));
	}
	
	public List<Key> listaCampos() {
		return key.findALL();
	}
	
	public Key getCampo(int id) {
		return key.findById(id);
	}
	
	public Key getCampo(String nome) {
		return key.findByNome(nome);
	}
	
	public String[] listaKey() {
		List<Key> lista_campos = key.findALL();
		
		String[] lista = new String[lista_campos.size()];
		for(int i=0; i<lista.length; i++) {
			lista[i] = lista_campos.get(i).getNome();
		}
		
		return lista;
	}
	
	public String[] listaValue(Usuario user) {
		List<Atributo> lista_atributo = user.getAtributo();
		List<Value> lista_valores = new ArrayList<Value>();
		for(int i=0; i<lista_atributo.size(); i++) {
			lista_valores.add(lista_atributo.get(i).getValue());
		}
		
		String[] lista = new String[lista_valores.size()];
		for(int i=0; i<lista.length; i++) {
			lista[i] = lista_valores.get(i).getConteudo();
		}
		
		return lista;
	}
	
	public Usuario getUsuarioById(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	public Usuario getUsuarioByUsername(String username) {
		return usuario.findByUsername(username);
	}
	
	public boolean temAutorizacao(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacao().size(); i++) {
			if(novo.getAutorizacao().get(i).getNome().equals("cad_campo"))
				return true;
		}
		
		return false;
	}
	
	public json_list getJsonList() {
		json_list lista = new json_list();
		return lista;
	}
	
	public class json_node {
		private Key key;

		public Key getTipo() {
			return key;
		}

		public void setKey(Key key) {
			this.key = key;
		}
		
		public String get() {
			String node = new String();
			
			if(this.key == null)
				node = "\"" + "id" + "\"" + ":" + "-1";
			else
				node = "\"" + "id" + "\"" + ":" + this.key.getId() + "," + "\"" + "nome" + "\"" + ":" + "\"" + this.key.getNome() + "\"";
			
			return node;
		}
		
		public void set(int item) {
			String temp = String.valueOf(item);
			this.key = new Key(temp);
		}
		
		public void set(Key item) {
			this.setKey(item);
		}
		
		public json_node() {
			this.key = new Key();
		}
	}
	
	public class json_list {
		private List<json_node> lista;

		public List<json_node> getLista() {
			return lista;
		}

		public void setLista(List<Key> lista) {
			int max = lista.size();
			
			for(int i=0; i<max; i++) {
				json_node e = new json_node();
				e.set(lista.get(i));
				this.lista.add(e);
			}
		}
		
		public String get() {
			int max = lista.size();
			String json = "{";
			for(int i=0; i<max-1; i++) {
				json_node temp = new json_node();
				temp.setKey(lista.get(i).getTipo());
				json = json + temp.get() + ",";
			}
			json_node temp = new json_node();
			temp.setKey(lista.get(max-1).getTipo());
			json = json + temp.get() + "}";
			return json;
		}
		
		public void set(int item) {
			json_node aux = new json_node();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public void set(Key item) {
			json_node aux = new json_node();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public json_list() {
			this.lista = new ArrayList<json_node>();
		}
	}
}
