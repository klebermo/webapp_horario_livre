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
	
	@Autowired
	private UsuarioHome usuario;
	
	@Autowired
	private AutorizacaoHome autorizacao;
	
	public boolean create_database(String maquina, String usuario, String senha) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("ClassNotFoundException");
		}
		try {
			String url = "jdbc:postgresql://"+maquina+"/postgres";
			System.out.println("url = "+url);
			System.out.println("usuario = "+usuario);
			System.out.println("senha = "+senha);
			Connection conn = DriverManager.getConnection(url,usuario,senha);
			Statement stmt = conn.createStatement();
			
		    ResultSet rs = stmt.executeQuery("SELECT count(*) FROM pg_catalog.pg_database WHERE datname = 'horario'");
		    rs.next();
		    int counter  = rs.getInt(1);
		    System.out.println("counter = "+counter);
		    if(counter > 0) {
				System.out.println("calling_create_tables");
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
		    System.out.println("result = "+result);
			if(result > 0) {
				System.out.println("calling_create_tables");
				rs.close();
				stmt.close();
				conn.close();
				create_tables(maquina, usuario, senha);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException");
			return false;
		}
		return false;
	}
	
	public void create_tables(String maquina, String usuario, String senha) {
		System.out.println("create_tables");
		create_properties(maquina, usuario, senha);
		
		Configuration config = new Configuration();
		Properties props = new Properties();
		FileInputStream fos;
		try {
			fos = new FileInputStream( "database.properties" );
			props.load(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config.setProperties(props);
		
		try {
			String url = props.getProperty("jdbc.url");
			Connection conn = DriverManager.getConnection(url,usuario,senha);
			SchemaExport schema = new SchemaExport(config, conn);
	        schema.create(true, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        insert_default_values();
	}
	
	public void insert_default_values() {
		System.out.println("insert_default_values");
		String [] autorizacoes = {"cad_evento", "lista_evento", "cad_horario", "lista_horario", "cad_usuario", "lista_usuario", "cad_campo", "cad_tipo", "cad_permissao"};
		for(int i=0; i<autorizacoes.length; i++) {
			autorizacao.persist(new Autorizacao(autorizacoes[i]));
		}
	}
	
	public void create_properties(String maquina, String usuario, String senha) {
		System.out.println("create_properties");
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
			fos = new FileOutputStream( "database.properties" );
			props.store( fos, "propriedades" );
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean create_user(String login, String senha, String pnome, String unome) {
		System.out.println("create_user");
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
