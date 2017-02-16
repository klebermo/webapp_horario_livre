package com.horariolivre.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AutorizacaoHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Autorizacao;
import com.horariolivre.entity.Usuario;

@Service
public class InstallService {
	
	private String property_file = "database.properties";
	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private AutorizacaoHome autorizacao;
	
	public boolean create_database(String maquina, String usuario, String senha) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String url = "jdbc:postgresql://"+maquina+"/postgres";
			Connection conn = DriverManager.getConnection(url,usuario,senha);
			Statement stmt = conn.createStatement();
			
		    ResultSet rs = stmt.executeQuery("SELECT count(*) FROM pg_catalog.pg_database WHERE datname = 'horario'");
		    rs.next();
		    int counter  = rs.getInt(1);
		    if(counter > 0) {
				rs.close();
				stmt.close();
				conn.close();
				create_tables(maquina, usuario, senha);
				return true;
		    }
			
		    stmt.executeUpdate("CREATE DATABASE horario WITH OWNER "+usuario);
		    rs = stmt.executeQuery("SELECT count(*) FROM pg_catalog.pg_database WHERE datname = 'horario'");
		    rs.next();
		    int result = rs.getInt(1);
			if(result > 0) {
				rs.close();
				stmt.close();
				conn.close();
				create_tables(maquina, usuario, senha);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public void create_tables(String maquina, String usuario, String senha) {
		create_properties(maquina, usuario, senha);
		
		Configuration config = new Configuration();
		Properties props = new Properties();
		FileInputStream fos;
		try {
			fos = new FileInputStream( this.property_file );
			props.load(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config.setProperties(props);
		
		config.addAnnotatedClass(com.horariolivre.entity.Atributo.class);
		config.addAnnotatedClass(com.horariolivre.entity.ConfigHorarioLivre.class);
		config.addAnnotatedClass(com.horariolivre.entity.Evento.class);
		config.addAnnotatedClass(com.horariolivre.entity.HorarioLivre.class);
		config.addAnnotatedClass(com.horariolivre.entity.Key.class);
		config.addAnnotatedClass(com.horariolivre.entity.Tipo.class);
		config.addAnnotatedClass(com.horariolivre.entity.Value.class);
		config.addAnnotatedClass(com.horariolivre.entity.Autorizacao.class);
		config.addAnnotatedClass(com.horariolivre.entity.Usuario.class);
		
		try {
			String url = props.getProperty("jdbc.url");
			Connection conn = DriverManager.getConnection(url,usuario,senha);
			SchemaExport schema = new SchemaExport();
	        //schema.create(true, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        insert_default_values();
	}
	
	public void insert_default_values() {
		String [] autorizacoes = {"cad_evento", "lista_evento", "cad_horario", "lista_horario", "cad_usuario", "lista_usuario", "cad_campo", "cad_tipo", "cad_permissao"};
		for(int i=0; i<autorizacoes.length; i++) {
			autorizacao.persist(new Autorizacao(autorizacoes[i]));
		}
	}
	
	public void create_properties(String maquina, String usuario, String senha) {
		Properties props = new Properties();
		
		props.setProperty("jdbc.Classname", "org.postgresql.Driver");
		props.setProperty("jdbc.url", "jdbc:postgresql://"+maquina+"/horario" );
		props.setProperty("jdbc.user", usuario );
		props.setProperty("jdbc.pass", senha );
		
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.setProperty("hibernate.show_sql", "false");
		props.setProperty("hibernate.hbm2ddl.auto", "update");

		FileOutputStream fos;
		try {
			fos = new FileOutputStream( this.property_file );
			props.store( fos, "propriedades" );
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean create_user(String login, String senha, String pnome, String unome) {
		Usuario novo = new Usuario(login, senha, pnome, unome);
		
		if(usuario.persist(novo))
			novo.setAutorizacao(autorizacao.findALL());
		else
			return false;
		
		if(usuario.merge(novo) != null)
			return true;
		else
			return false;
	}
}
