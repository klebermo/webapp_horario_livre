package com.horariolivre.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

// Generated 24/03/2014 06:49:18 by Hibernate Tools 3.4.0.CR1

/**
 * DadosUsuario generated by hbm2java
 */

@Entity
@Table(name = "key")
public class Value implements java.io.Serializable {

	private static final long serialVersionUID = 6515224281217075194L;
	
	private int id;
	private String conteudo;

	public Value() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}
