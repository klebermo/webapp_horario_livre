package com.horariolivre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public boolean cadastra(String tipoUsuario) {
		return tipo.persist(new Tipo(Integer.valueOf(tipoUsuario).intValue()));
	}
	
	public boolean remover(String tipoUsuario) {
		return tipo.remove(this.tipo.findById(Integer.valueOf(tipoUsuario).intValue()));
	}
	
	public List<Tipo> listaTipos() {
		return tipo.findALL();
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
			if(novo.getAutorizacao().get(i).getNome().equals("cad_tipo"))
				return true;
		}
		
		return false;
	}
}
