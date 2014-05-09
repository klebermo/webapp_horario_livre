package com.horariolivre.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AutorizacaoHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Autorizacao;
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
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			String url = "jdbc:postgresql://"+maquina+"/postgres";
			Connection conn = DriverManager.getConnection(url,usuario,senha);
			
			Statement stmt = conn.createStatement();
		    String sql = "SELECT * FROM pg_database";
		    ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString("datname").equals("horario"))
					return false;
			}
			
		    sql = "CREATE DATABASE horario WITH OWNER = "+usuario+" ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'pt_BR.utf8' LC_CTYPE = 'pt_BR.utf8' CONNECTION LIMIT = -1;";
			stmt.executeUpdate(sql);

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.getStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean create_user(String login, String senha, String pnome, String unome) {
		Usuario novo = new Usuario(login, senha, pnome, unome);
		novo.setAutorizacao(autorizacao.findALL());
		return usuario.persist(novo);
	}
}
