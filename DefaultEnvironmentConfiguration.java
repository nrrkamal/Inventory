package com.configuration;

import org.springframework.context.annotation.Bean;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = { "classpath:jndi.properties"})
public class DefaultEnvironmentConfiguration {
	
	private static final String DATABASE_JNDI_NAME = "jdbc.datasource";
	@Autowired(required=true)
	private Environment environment;

	@Bean
	public DataSource dataSource() throws NamingException {
		return (DataSource) new InitialContext().lookup("java:comp/env/" + environment.getProperty(DATABASE_JNDI_NAME));
	}

}
