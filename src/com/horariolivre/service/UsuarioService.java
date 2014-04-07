package com.horariolivre.service;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AutorizacaoHome;
import com.horariolivre.dao.ConfigHorarioLivreHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Autorizacao;
import com.horariolivre.entity.ConfigHorarioLivre;
import com.horariolivre.entity.Tipo;
import com.horariolivre.entity.Usuario;

@Service
public class UsuarioService {	
	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private AutorizacaoHome autorizacao;
	
	@Autowired
	private ConfigHorarioLivreHome config;
		
	public boolean cadastra(String login, String senha, String primeiroNome, String ultimoNome, Tipo tipoUsuario, String[] key, String[] value) {
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
}
