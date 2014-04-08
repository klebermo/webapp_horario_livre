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
		
		if(!senha.isEmpty())
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
	
	public json_list_auth getJsonListAuth() {
		json_list_auth lista = new json_list_auth();
		return lista;
	}
	
	public class json_node_auth {
		private Autorizacao auth;
		
		public Autorizacao getAuth() {
			return auth;
		}
		
		public void setAuth(Autorizacao auth) {
			this.auth = auth;
		}
		
		public String get() {
			String node = new String();
			
			if(this.auth == null)
				node = "\"" + "id" + "\"" + ":" + "-1";
			else
				node = "\"" + "id" + "\"" + ":" + this.auth.getId() + "," + "\"" + "nome" + "\"" + ":" + "\"" + this.auth.getNome() + "\"";
			
			return node;
		}
		
		public void set(Autorizacao item) {
			this.setAuth(item);
		}
		
		public json_node_auth() {
			this.auth = new Autorizacao();
		}
	}
	
	public class json_list_auth {
		private List<json_node_auth> lista;

		public List<json_node_auth> getLista() {
			return lista;
		}

		public void setLista(List<Autorizacao> lista) {
			int max = lista.size();
			
			for(int i=0; i<max; i++) {
				json_node_auth e = new json_node_auth();
				e.set(lista.get(i));
				this.lista.add(e);
			}
		}
		
		public String get() {
			int max = lista.size();
			String json = "{\"Auth\":[";
			for(int i=0; i<max-1; i++) {
				json = json + "{";
				json_node_auth temp = new json_node_auth();
				temp.setAuth(lista.get(i).getAuth());
				json = json + temp.get() + "},";
			}
			json_node_auth temp = new json_node_auth();
			temp.setAuth(lista.get(max-1).getAuth());
			json = json + "{" + temp.get() + "}]}";
			return json;
		}
		
		public void set(Autorizacao item) {
			json_node_auth aux = new json_node_auth();
			aux.set(item);
			this.lista.add(aux);
		}
		
		public json_list_auth() {
			this.lista = new ArrayList<json_node_auth>();
		}
	}
}
