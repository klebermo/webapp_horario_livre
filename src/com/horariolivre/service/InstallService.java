package com.horariolivre.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AutorizacaoHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Usuario;

@Service
@PropertySource("classpath:database.properties")
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
			
		    ResultSet rs = stmt.executeQuery("SELECT count(*) FROM pg_catalog.pg_database WHERE datname = 'horario';");
		    rs.next();
		    int counter  = rs.getInt(1);
		    System.out.println("counter = "+counter);
		    if(counter > 0)
		    	return true;
			
		    int result = stmt.executeUpdate("CREATE DATABASE horario WITH OWNER "+usuario+";");
		    System.out.println("result = "+result);
			if(result > 0) {
				rs.close();
				stmt.close();
				conn.close();
				return true;
			}
			
			//create_tables(maquina, usuario, senha);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException");
			return false;
		}
		return false;
	}
	
	public void create_tables(String maquina, String usuario, String senha) {
		Configuration config = new Configuration();
        SchemaExport schema = new SchemaExport(config);
        schema.create(true, true);
	}
	
	public boolean create_user(String login, String senha, String pnome, String unome) {
		Usuario novo = new Usuario(login, senha, pnome, unome);
		novo.setAutorizacao(autorizacao.findALL());
		return usuario.persist(novo);
	}
}
