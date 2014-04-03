package com.horariolivre.entity;

// Generated 24/03/2014 06:49:18 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Dados generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dados")
public class Dados implements java.io.Serializable {

	private int id;
	private String campo;

	public Dados() {
	}

	public Dados(int id) {
		this.id = Integer.valueOf(id).intValue();
	}
	
	public Dados(String campo) {
		this.campo = campo;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "campo", nullable = false, length = 16)
	public String getCampo() {
		return this.campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}
}
