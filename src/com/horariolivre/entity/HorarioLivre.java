package com.horariolivre.entity;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// Generated 24/03/2014 06:49:18 by Hibernate Tools 3.4.0.CR1

/**
 * HorarioLivre generated by hbm2java
 */

@Entity
@Table(name = "horario_livre")
public class HorarioLivre implements java.io.Serializable {

	private static final long serialVersionUID = 9031623789021514739L;
	
	private int id;
	private Date data;
	private Time hora;
	private Usuario usuario;

	public HorarioLivre() {
	}
	
	public HorarioLivre(Date data, Time hora, Usuario usuario) {
		this.setData(data);
		this.setHora(hora);
		this.setUsuario(usuario);
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "data")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "hora")
	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	@OneToOne
	@JoinColumn(name="fk_usuario")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
