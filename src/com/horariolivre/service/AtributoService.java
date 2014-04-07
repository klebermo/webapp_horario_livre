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
		return key.remove(new Key(campo));
	}
	
	public List<Key> listaCampos() {
		return key.findALL();
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
	
	public class json_node {
		private Key key;
		public Key getKey() {
			return key;
		}
		public void setKey(Key key) {
			this.key = key;
		}
		public String get() {
			return "\""+key.getId()+"\":\""+key.getNome()+"\"";
		}
	}
	
	public class json_list {
		private List<Atributo> lista;

		public List<Atributo> getLista() {
			return lista;
		}

		public void setLista(List<Atributo> lista) {
			this.lista = lista;
		}
		
		public String get() {
			int max = lista.size();
			String json = "{";
			for(int i=0; i<max-1; i++) {
				json_node temp = new json_node();
				temp.setKey(lista.get(i).getKey());
				json = json + temp.get() + ",";
			}
			json_node temp = new json_node();
			temp.setKey(lista.get(max-1).getKey());
			json = json + temp.get() + "}";
			return json;
		}
	}
}
