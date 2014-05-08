package com.horariolivre.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horariolivre.dao.AutorizacaoHome;
import com.horariolivre.dao.UsuarioHome;
import com.horariolivre.entity.Usuario;

@Service
public class InstallService {
	
	@Autowired
	UsuarioHome usuario;
	
	@Autowired
	AutorizacaoHome autorizacao;
	
	public boolean create_database(String maquina, String usuario, String senha) {
		Configuration config = new Configuration();
		Properties property = new Properties();
		
		config.setProperty("jdbc.Classname", "org.postgresql.Driver");
		config.setProperty("jdbc.url", "jdbc:postgresql://"+maquina+"/horario?charSet=LATIN1");
		config.setProperty("jdbc.user", usuario);
		config.setProperty("jdbc.pass", senha);
		
		property.setProperty("jdbc.Classname", "org.postgresql.Driver");
		property.setProperty("jdbc.url", "jdbc:postgresql://"+maquina+"/horario?charSet=LATIN1");
		property.setProperty("jdbc.user", usuario);
		property.setProperty("jdbc.pass", senha);
		property.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		property.setProperty("hibernate.show_sql", "false");
		property.setProperty("hibernate.hbm2ddl.auto", "update");
		
		File file = new File("classpath:persistence.properties");
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			property.store(fileOut, "database properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SchemaExport schema = new SchemaExport(config);
		schema.create(true, true);
		return true;
	}
	
	public boolean create_user(String login, String senha, String pnome, String unome) {
		Usuario novo = new Usuario(login, senha, pnome, unome);
		novo.setAutorizacao(autorizacao.findALL());
		return usuario.persist(novo);
	}
}
