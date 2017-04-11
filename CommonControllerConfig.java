package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.services.EmployeeService;


@Configuration
@ComponentScan({"com.configuration","com.services","com.repository","com.entity","com.controller","com.domain"})
public class CommonControllerConfig {	 
	
	@Bean(name="employeeService")
	public EmployeeService getEmployeeService() {
		return new EmployeeService();
	}
}
