package com.horariolivre.service;

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
	UsuarioHome usuario;
	
	@Autowired
	AutorizacaoHome autorizacao;
	
	public boolean create_database(String maquina, String usuario, String senha) {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/postgres?user="+usuario+"&password="+senha;
			Connection conn = null;
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM pg_database";
		    ResultSet rs = stmt.executeQuery(sql);
		    while(rs.next()) {
		    	System.out.println("rs = "+rs.toString());
		    	
				if(rs.getString("datname").equals("horario"))
					return false;
			}
		    sql = "CREATE DATABASE horario WITH OWNER = "+usuario+" ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'pt_BR.utf8' LC_CTYPE = 'pt_BR.utf8' CONNECTION LIMIT = -1;";
		    if(stmt.executeUpdate(sql) == 0)
		    	return false;
		    rs.close();
		    stmt.close();
		    conn.close();
		} catch (SQLException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			e.getStackTrace();
		}
		
		return create_tables(maquina, usuario, senha);
	}
	
	public boolean create_tables(String maquina, String usuario, String senha) {
		Configuration config = new Configuration();
		config.setProperty("jdbc.Classname", "org.postgresql.Driver");
		config.setProperty("jdbc.url", "jdbc:postgresql://"+maquina+"/horario?charSet=LATIN1");
		config.setProperty("jdbc.user", usuario);
		config.setProperty("jdbc.pass", senha);
		config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		config.setProperty("hibernate.show_sql", "false");
		config.setProperty("hibernate.hbm2ddl.auto", "create");
		
		SchemaExport schema = new SchemaExport(config);
		schema.setOutputFile("database.properties");
		schema.create(true, true);
		
		Properties properties = new Properties();
		//
		properties.setProperty("jdbc.Classname", "org.postgresql.Driver");
		properties.setProperty("jdbc.url", "jdbc:postgresql://"+maquina+"/horario?charSet=LATIN1");
		properties.setProperty("jdbc.user", usuario);
		properties.setProperty("jdbc.pass", senha);
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "false");
		properties.setProperty("hibernate.hbm2ddl.auto", "validate");
		
		return autorizacao.persist(new Autorizacao("permissao_teste"));
	}
	
	public boolean create_user(String login, String senha, String pnome, String unome) {
		Usuario novo = new Usuario(login, senha, pnome, unome);
		novo.setAutorizacao(autorizacao.findALL());
		return usuario.persist(novo);
	}
}
