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
	
	public boolean altera(HorarioLivre horario) {
		if( horariolivre.merge(horario) != null )
			return true;
		return false;
	}
	
	public List<HorarioLivre> lista(int id_usuario) {
		return horariolivre.findByUsuario(usuario.findById(id_usuario));
	}
}
