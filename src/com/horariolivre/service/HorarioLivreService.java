package com.horariolivre.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.horariolivre.dao.HorarioLivreHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.HorarioLivre;
import com.horariolivre.entity.Usuario;

@Component
public class HorarioLivreService {
	@Autowired
	private HorarioLivreHome horariolivre;
	
	@Autowired
	private UsuarioHome usuario;
	
	public boolean cadastra(Date data, Time hora, int id_usuario) {
		Usuario owner = usuario.findById(id_usuario);
		HorarioLivre horario = new HorarioLivre(data, hora, owner);
		return horariolivre.persist(horario);
	}
	
	public boolean remove(HorarioLivre horario) {
		return horariolivre.remove(horario);
	}
	
	public boolean existe(Date data, Time hora, String username) {
		if(horariolivre.findByExample(new HorarioLivre(data, hora, this.getUsuarioByUsername(username))) != null)
			return true;
		return false;
	}
	public List<HorarioLivre> lista(int id_usuario) {
		return horariolivre.findByUsuario(usuario.findById(id_usuario));
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
			if(novo.getAutorizacao().get(i).getNome().equals("cad_horario"))
				return true;
		}
		
		return false;
	}
	
	public boolean temAutorizacaoListagem(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacao().size(); i++) {
			if(novo.getAutorizacao().get(i).getNome().equals("lista_horario"))
				return true;
		}
		
		return false;
	}
	
}
