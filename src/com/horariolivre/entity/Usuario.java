package com.horariolivre.entity;

// Generated 24/03/2014 06:49:18 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Usuario generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "usuario")
public class Usuario implements java.io.Serializable {

	private int id;
	private String login;
	private String senha;
	private String primeiroNome;
	private String ultimoNome;
	private List<TipoUsuario> tipoUsuarios = new ArrayList<TipoUsuario>();
	private List<DadosUsuario> dadosUsuarios = new ArrayList<DadosUsuario>();
	private List<Autorizacoes> autorizacoes = new ArrayList<Autorizacoes>();
	private ConfigHorarioLivre config;

	public Usuario() {
	}

	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public Usuario(String login, String senha, String primeiroNome, String ultimoNome) {
		this.login = login;
		this.senha = senha;
		this.primeiroNome = primeiroNome;
		this.ultimoNome = ultimoNome;
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

	@Column(name = "login", nullable = false, length = 16)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "senha", nullable = false)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Column(name = "primeiro_nome", length = 32)
	public String getPrimeiroNome() {
		return this.primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	@Column(name = "ultimo_nome", length = 32)
	public String getUltimoNome() {
		return this.ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "tipo_usuario", joinColumns = { @JoinColumn(name = "fk_usuario") }, inverseJoinColumns = { @JoinColumn(name = "fk_tipo") })
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<TipoUsuario> getTipoUsuarios() {
		return this.tipoUsuarios;
	}

	public void setTipoUsuarios(List<TipoUsuario> tipoUsuarios) {
		this.tipoUsuarios = tipoUsuarios;
	}

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "autorizacoes_usuario", joinColumns = { @JoinColumn(name = "fk_usuario") }, inverseJoinColumns = { @JoinColumn(name = "fk_autorizacoes") })
	//@LazyCollection(LazyCollectionOption.TRUE)
	public List<Autorizacoes> getAutorizacoes() {
		return this.autorizacoes;
	}

	public void setAutorizacoes(List<Autorizacoes> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "dados_usuario", joinColumns = { @JoinColumn(name = "fk_usuario") }, inverseJoinColumns = { @JoinColumn(name = "fk_dados") })
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<DadosUsuario> getDadosUsuarios() {
		return this.dadosUsuarios;
	}

	public void setDadosUsuarios(List<DadosUsuario> dadosUsuarios) {
		this.dadosUsuarios = dadosUsuarios;
	}

	@OneToOne
	@JoinColumn(name="fk_config")
	public ConfigHorarioLivre getConfig() {
		return config;
	}

	public void setConfig(ConfigHorarioLivre config) {
		this.config = config;
	}

}
