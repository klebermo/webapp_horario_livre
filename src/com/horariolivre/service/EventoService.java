package com.horariolivre.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.horariolivre.dao.AutorizacoesUsuarioHome;
import com.horariolivre.dao.EventoHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Evento;
import com.horariolivre.entity.Usuario;

@Component
public class EventoService {
	@Autowired
	private EventoHome evento;
	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private AutorizacoesUsuarioHome autorizacoes;
	
	public boolean cadastra(int id_usuario, String nome, String descricao, Date dataInicial, Date dataFinal, Time horaInicial, Time horaFinal, int duracao) {
		Usuario owner = usuario.findById(id_usuario);
		Evento novo = new Evento(owner, nome, descricao, dataInicial, dataFinal, horaInicial, horaFinal, duracao);
		return this.evento.persist(novo);
	}
	
	public boolean remove(Evento apaga) {
		return this.evento.remove(apaga);
	}
	
	public boolean altera(Evento novo) {
		if (this.evento.merge(novo) == null)
			return false;
		else
			return true;
	}
	
	public List<Evento> lista() {
		return evento.findALL();
	}
	
	public Evento getEvento(int id_evento) {
		return evento.findById(id_evento);
	}
	
	public boolean temAutorizacaoCadastro(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacoesUsuarios().size(); i++) {
			if(novo.getAutorizacoesUsuarios().get(i).getAutorizacoes().getNome().equals("cad_evento"))
				return true;
		}
		
		return false;
	}
	
	public boolean temAutorizacaoListagem(int id_usuario) {
		Usuario novo = usuario.findById(id_usuario);
		
		for(int i=0; i<novo.getAutorizacoesUsuarios().size(); i++) {
			if(novo.getAutorizacoesUsuarios().get(i).getAutorizacoes().getNome().equals("lista_evento"))
				return true;
		}
		
		return false;
	}
}
