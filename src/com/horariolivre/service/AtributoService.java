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
	
	public Key getCampo(String nome) {
		return key.findByNome(nome);
	}
	
	public Value getValor(String campo, int id_usuario) {
		Usuario user = this.getUsuarioById(id_usuario);
		List<Value> lista = this.listaValores(user.getLogin());
		int max = lista.size();
		for(int i=0; i<max; i++) {
			if(lista.get(i).getConteudo().equals(campo))
				return lista.get(i);
		}
		return null;
	}
	
	public List<Key> listaCampos() {
		return key.findALL();
	}
	
	public List<Value> listaValores(String username) {
		Usuario user = this.getUsuarioByUsername(username);
		List<Atributo> lista = user.getAtributo();
		List<Value> lista_valores = new ArrayList<Value>();
		
		int max = lista.size();
		for(int i=0; i< max; i++) {
			lista_valores.add(lista.get(i).getValue());
		}
		return lista_valores;
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
		
		private Value value;

		public Key getKey() {
			return key;
		}

		public void setKey(Key key) {
			this.key = key;
		}
		
		public Value getValue() {
			return value;
		}
		
		public void setValue(Value value) {
			this.value = value;
		}
		
		public String get() {
			String node = new String();
			
			if(this.key == null) {
				if(this.value == null) {
					node = "\"" + "id" + "\"" + ":" + "\"" + "-1" + "\"" + "," + "\"" + "value" + "\"" + ":" + "\"" + " " + "\"";
				}
				else {
					node = "\"" + "id" + "\"" + ":" + "\"" + "-1" + "\"" + "," + "\"" + "value" + "\"" + ":" + "\"" + this.value.getConteudo() + "\"";
				}
			}
			else {
				if(this.value == null) {
					node = "\"" + "key" + "\"" + ":" + "\"" + this.key.getNome() + "\"" + "," + "\"" + "value" + "\"" + ":" + "\"" + " " + "\"";
				}
				else {
					node = "\"" + "key" + "\"" + ":" + "\"" + this.key.getNome() + "\"" + "," + "\"" + "value" + "\"" + ":" + "\"" + this.value.getConteudo() + "\"";
				}
			}
			
			return node;
		}
		
		public void set(int item) {
			String temp = String.valueOf(item);
			this.key = new Key(temp);
		}
		
		public void set(Key item, Value item2) {
			this.setKey(item);
			this.setValue(item2);
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
				e.set(lista.get(i), null);
				this.lista.add(e);
			}
		}
		
		public void setLista(List<Key> lista1, List<Value> lista2) {
			int max = lista1.size();
			int max_valores = lista2.size();
			
			for(int i=0; i<max; i++) {
				json_node e = new json_node();
				if(i >= max_valores)
					e.set(lista1.get(i), new Value());
				else
					e.set(lista1.get(i), lista2.get(i));
				this.lista.add(e);
			}
		}
		
		public String get() {
			int i, max = lista.size();
			System.out.println("max="+max);
			String json = "{\"Key\":[";
			for(i=0; i<max-1; i++) {
				json = json + "{";
				json_node temp = new json_node();
				temp.set(lista.get(i).getKey(), lista.get(i).getValue());
				json = json + temp.get() + "},";
			}
			json_node temp = new json_node();
			temp.set(lista.get(i).getKey(), lista.get(i).getValue());
			json = json + "{" + temp.get() + "}]}";
			return json;
		}
				
		public void set(int item) {
			json_node aux = new json_node();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public void set(Key item, Value item2) {
			json_node aux = new json_node();
			aux.set(item, item2);
			this.lista.add(aux);
		}
		
		public json_list() {
			this.lista = new ArrayList<json_node>();
		}
	}
}
