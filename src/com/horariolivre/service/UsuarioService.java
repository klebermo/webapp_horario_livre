package com.horariolivre.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AutorizacoesHome;
import com.horariolivre.dao.AutorizacoesUsuarioHome;
import com.horariolivre.dao.DadosHome;
import com.horariolivre.dao.DadosUsuarioHome;
import com.horariolivre.dao.TipoHome;
import com.horariolivre.dao.TipoUsuarioHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Autorizacoes;
import com.horariolivre.entity.AutorizacoesUsuario;
import com.horariolivre.entity.Dados;
import com.horariolivre.entity.DadosUsuario;
import com.horariolivre.entity.Tipo;
import com.horariolivre.entity.TipoUsuario;
import com.horariolivre.entity.Usuario;

@Service
public class UsuarioService {	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private TipoHome tipo;
	
	@Autowired
	private TipoUsuarioHome tipo_usuario;
	
	@Autowired
	private DadosHome dados;
	
	@Autowired
	private DadosUsuarioHome dados_usuario;
	
	@Autowired
	private AutorizacoesHome autorizacoes;
	
	@Autowired
	private AutorizacoesUsuarioHome autorizacao_usuario;
	
	public boolean cadastra(String login, String senha, String primeiroNome, String ultimoNome, String tipoUsuario, String[] conteudo) {
		List<Dados> lista_dados = dados.findALL();
		String[] campos = new String[lista_dados.size()];
		String[] conteudo2 = new String[lista_dados.size()];
		
		for(int i=0; i<lista_dados.size(); i++) {
			campos[i] = String.valueOf(lista_dados.get(i).getId());
		}
		
		for(int i=0; i<lista_dados.size(); i++) {
			conteudo2[i] = " ";
		}
		
		Usuario novo = new Usuario(login, senha, primeiroNome, ultimoNome, tipoUsuario, campos, conteudo2);
		if(usuario.merge(novo) != null)
			return true;
		else
			return false;
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
	
	public List<TipoUsuario> listaTipoUsuario() {
		return tipo_usuario.findALL();
	}
	
	public List<Dados> listaDados() {
		return dados.findALL();
	}
	
	public List<DadosUsuario> listaDadosUsuario() {
		return dados_usuario.findALL();
	}
	
	public List<Autorizacoes> listaAutorizacoes() {
		return autorizacoes.findALL();
	}
	
	public List<AutorizacoesUsuario> listaAutorizacoesUsuario() {
		return autorizacao_usuario.findALL();
	}
	
	public Usuario getUsuario(int id_usuario) {
		return usuario.findById(id_usuario);
	}
	
	public boolean temAutorizacaoCadastro(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacoes().size(); i++) {
			if(novo.getAutorizacoes().get(i).getNome().equals("cad_usuario"))
				return true;
		}
		
		return false;
	}
	
	public boolean temAutorizacaoListagem(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacoes().size(); i++) {
			if(novo.getAutorizacoes().get(i).getNome().equals("lista_usuario"))
				return true;
		}
		
		return false;
	}
}
