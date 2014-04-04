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
		
	public boolean cadastra(String login, String senha, String primeiroNome, String ultimoNome, Tipo tipoUsuario, String[] key, String[] value) {
		System.out.println("UsuarioService.cadastra");
		 return this.usuario.persist(new Usuario(login, senha, primeiroNome, ultimoNome, tipoUsuario, key, value));
	}
	
	public boolean remove(Usuario usuario) {
		return this.usuario.remove(usuario);
	}
	
	public boolean altera(Usuario usuario) {
		if(this.usuario.merge(usuario) != null)
			return true;
		return false;
	}
	
	public List<Usuario> lista() {
		return usuario.findALL();
	}
	
	public List<Tipo> listaTipos() {
		return tipo.findALL();
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
	
	public boolean cadastra_campo(String campo) {
		return key.persist(new Key(campo));
	}
	
	public boolean cadastra_tipo(String tipo_usuario) {
		return tipo.persist(new Tipo(tipo_usuario));
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
		
	public List<Autorizacao> listaAutorizacoes() {
		return autorizacao.findALL();
	}
	
	public List<Autorizacao> listaAutorizacoesUsuario(int id_usuario) {
		return usuario.findById(id_usuario).getAutorizacao();
	}
	
	public Usuario getUsuario(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	public boolean salva_config(Time data_inicial, Time data_final) {
		return config.persist(new ConfigHorarioLivre(data_inicial, data_final));
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
}
