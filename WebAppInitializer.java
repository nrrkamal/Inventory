package com.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * The Initializer class for the application
 * 
 * @author
 * @since 
 * @version
 */
public class WebAppInitializer implements WebApplicationInitializer {

	/**
	 * Create the Spring application context, dispatcher servlet and manage the
	 * lifecycle of the application context
	 * 
	 * @param container
	 *            the Servlet Context
	 */
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();		 
		appContext.register(ApplicationConfig.class);
		appContext.scan("com.configuration");		
		ContextLoaderListener contextLoaderListener = new ContextLoaderListener(appContext);
		container.addListener(contextLoaderListener);
		container.setInitParameter("contextInitializerClasses", "com.configuration.POCApplicationContextInitializer");
		/*// Creates the Spring Container shared by all Servlets and Filters
		ConfigurableEnvironment environment = appContext.getEnvironment();
		container.addListener(new ContextLoaderListener(appContext));*/		 
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher-servlet", new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
	    dispatcher.addMapping("/");		
	}	
	 
}