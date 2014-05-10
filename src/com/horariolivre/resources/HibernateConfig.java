package com.horariolivre.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
@ComponentScan("com.horariolivre.dao")
public class HibernateConfig {
	
	private String property_file = "database.properties";

   @Autowired
   private Environment env;

   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(restDataSource());
      sessionFactory.setPackagesToScan("com.horariolivre.entity");
      sessionFactory.setHibernateProperties(hibernateProperties());
      return sessionFactory;
   }

   @Bean
   public DataSource restDataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      
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
		
      dataSource.setDriverClassName(props.getProperty("jdbc.Classname"));
      dataSource.setUrl(props.getProperty("jdbc.url"));
      dataSource.setUsername(props.getProperty("jdbc.user"));
      dataSource.setPassword(props.getProperty("jdbc.pass"));

      return dataSource;
   }

   @Bean
   public HibernateTransactionManager transactionManager() {
      HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory().getObject());

      return txManager;
   }

   Properties hibernateProperties() {
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
	   
	   props.setProperty("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto"));
	   props.setProperty("hibernate.show_sql", props.getProperty("hibernate.show_sql"));
	   props.setProperty("hibernate.dialect", props.getProperty("hibernate.dialect"));
	   
	   return props;
   }

}
