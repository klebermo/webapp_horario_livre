package com.horariolivre.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AtributoHome;
import com.horariolivre.dao.AutorizacaoHome;
import com.horariolivre.dao.ConfigHorarioLivreHome;
import com.horariolivre.dao.KeyHome;
import com.horariolivre.dao.TipoHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.dao.ValueHome;
import com.horariolivre.entity.Atributo;
import com.horariolivre.entity.Autorizacao;
import com.horariolivre.entity.ConfigHorarioLivre;
import com.horariolivre.entity.Key;
import com.horariolivre.entity.Tipo;
import com.horariolivre.entity.Usuario;
import com.horariolivre.entity.Value;

@Service
public class UsuarioService {	
	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private TipoHome tipo;
	
	@Autowired
	private AtributoHome atributo;
	
	@Autowired
	private KeyHome key;
	
	@Autowired
	private ValueHome value;
	
	@Autowired
	private AutorizacaoHome autorizacao;
	
	@Autowired
	private ConfigHorarioLivreHome config;
		
	public boolean cadastra(String login, String senha, String primeiroNome, String ultimoNome, String tipoUsuario, String[] campo, String[] valor) {
		Tipo tipo_usuario = tipo.findByNome(tipoUsuario);
		List<Atributo> lista_atributos = new ArrayList<Atributo>();
		
		for(int i=0; i<campo.length; i++) {
			Key chave = key.findByNome(campo[i]);
			Value conteudo = new Value(valor[i]);
			lista_atributos.add(new Atributo(chave, conteudo));
		}
		
		return this.usuario.persist(new Usuario(login, senha, primeiroNome, ultimoNome, tipo_usuario, lista_atributos));
	}
	
	public boolean remove(Usuario usuario) {
		return this.usuario.remove(usuario);
	}
	
	public boolean altera(Usuario user, String senha, String primeiroNome, String ultimoNome, String tipoUsuario, String[] campo, String[] valor) {
		Tipo tipo_usuario = tipo.findByNome(tipoUsuario);
		List<Atributo> lista_atributos = new ArrayList<Atributo>();
		
		for(int i=0; i<campo.length; i++) {
			Key chave = key.findByNome(campo[i]);
			Value conteudo = new Value(valor[i]);
			lista_atributos.add(new Atributo(chave, conteudo));
		}
		
		user.setSenha(senha);
		user.setTipo(tipo_usuario);
		user.setPrimeiroNome(primeiroNome);
		user.setUltimoNome(ultimoNome);
		user.setAtributo(lista_atributos);
		
		if(this.usuario.merge(user) != null)
			return true;
		else
			return false;
			
	}
	
	public boolean altera(Usuario usuario) {
		if(this.usuario.merge(usuario) != null)
			return true;
		return false;
	}
	
	public boolean salva_config(Time horaInicial, Time horaFinal, Usuario user) {
		ConfigHorarioLivre config_horario = user.getConfig();
		config_horario.setHoraInicial(horaInicial);
		config_horario.setHoraFinal(horaFinal);
		
		if(config.merge(config_horario) != null) {
			user.setConfig(config_horario);
			if(usuario.merge(user) != null)
				return true;
			else
				return false;
		}
		else {
			return false;
		}
	}
	
	public List<Usuario> lista() {
		return usuario.findALL();
	}
		
	public List<Autorizacao> listaAutorizacoes() {
		return autorizacao.findALL();
	}
	
	public List<Autorizacao> listaAutorizacoesUsuario(int id_usuario) {
		return usuario.findById(id_usuario).getAutorizacao();
	}
	
	public Usuario getUsuarioById(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	public Usuario getUsuarioByUsername(String username) {
		return usuario.findByUsername(username);
	}
	
	public boolean temAutorizacaoCadastro(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacao().size(); i++) {
			if(novo.getAutorizacao().get(i).getNome().equals("cad_usuario"))
				return true;
		}
		
		return false;
	}
	
	public boolean temAutorizacaoListagem(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacao().size(); i++) {
			if(novo.getAutorizacao().get(i).getNome().equals("lista_usuario"))
				return true;
		}
		
		return false;
	}
	
	public json_list_tipo getJsonListTipo() {
		json_list_tipo lista = new json_list_tipo();
		return lista;
	}
	
	public json_list_key getJsonListKey() {
		json_list_key lista = new json_list_key();
		return lista;
	}
	
	public class json_node_usuario {
		private Usuario usuario;

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
		
		public String get() {
			String node = new String();
			
			if(this.usuario == null)
				node = "\"" + "id" + "\"" + ":" + "-1";
			else {
				node = "\"" + "id" + "\"" + ":" + this.usuario.getId() + ",";
				node += "\"" + "pnome" + "\"" + ":" + "\"" + this.usuario.getPrimeiroNome() + "\"" + ",";
				node += "\"" + "unome" + "\"" + ":" + "\"" + this.usuario.getUltimoNome() + "\"" + ",";
			}
			
			return node;
		}
	}
	
	public class json_node_tipo {
		private Tipo tipo;

		public Tipo getTipo() {
			return tipo;
		}

		public void setTipo(Tipo tipo) {
			this.tipo = tipo;
		}
		
		public String get() {
			String node = new String();
			
			if(this.tipo == null)
				node = "\"" + "id" + "\"" + ":" + "-1";
			else
				node = "\"" + "id" + "\"" + ":" + this.tipo.getId() + "," + "\"" + "nome" + "\"" + ":" + "\"" + this.tipo.getNome() + "\"";
			
			return node;
		}
		
		public void set(Tipo item) {
			this.setTipo(item);
		}
		
		public json_node_tipo() {
			this.tipo = new Tipo();
		}
	}
	
	public class json_list_tipo {
		private List<json_node_tipo> lista;

		public List<json_node_tipo> getLista() {
			return lista;
		}

		public void setLista(List<Tipo> lista) {
			int max = lista.size();
			
			for(int i=0; i<max; i++) {
				json_node_tipo e = new json_node_tipo();
				e.set(lista.get(i));
				this.lista.add(e);
			}
		}
		
		public String get() {
			int max = lista.size();
			String json = "{\"Tipo\":[";
			for(int i=0; i<max-1; i++) {
				json = json + "{";
				json_node_tipo temp = new json_node_tipo();
				temp.setTipo(lista.get(i).getTipo());
				json = json + temp.get() + "},";
			}
			json_node_tipo temp = new json_node_tipo();
			temp.setTipo(lista.get(max-1).getTipo());
			json = json + "{" + temp.get() + "}]}";
			return json;
		}
		
		public void set(Tipo item) {
			json_node_tipo aux = new json_node_tipo();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public json_list_tipo() {
			this.lista = new ArrayList<json_node_tipo>();
		}
	}
	
	public class json_node_key {
		private Key key;

		public Key getKey() {
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
		
		public json_node_key() {
			this.key = new Key();
		}
	}
	
	public class json_list_key {
		private List<json_node_key> lista;

		public List<json_node_key> getLista() {
			return lista;
		}

		public void setLista(List<Key> lista) {
			int max = lista.size();
			
			for(int i=0; i<max; i++) {
				json_node_key e = new json_node_key();
				e.set(lista.get(i));
				this.lista.add(e);
			}
		}
		
		public String get() {
			int max = lista.size();
			String json = "{\"Key\":[";
			for(int i=0; i<max-1; i++) {
				json = json + "{";
				json_node_key temp = new json_node_key();
				temp.setKey(lista.get(i).getKey());
				json = json + temp.get() + "},";
			}
			json_node_key temp = new json_node_key();
			temp.setKey(lista.get(max-1).getKey());
			json = json + "{" + temp.get() + "}]}";
			return json;
		}
		
		public void set(int item) {
			json_node_key aux = new json_node_key();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public void set(Key item) {
			json_node_key aux = new json_node_key();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public json_list_key() {
			this.lista = new ArrayList<json_node_key>();
		}
	}
	
}
