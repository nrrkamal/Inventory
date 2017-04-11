package com.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.services.EmployeeService;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Spring configuration for the web application.
 * 
 * @author  
 * @since  
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@EnableJpaRepositories({"com.repository", "com.entity"})
@ComponentScan({"com.configuration","com.services","com.repository","com.entity","com.controller","com.domain"})
public class ApplicationConfig extends WebMvcConfigurerAdapter { 
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ResourceLoader resourceLoader;

	private static final String HIBERNATE_DIALECT = "hibernate.dialect";

	private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	
	 public static void main(String[] args) {
		  new SpringApplicationBuilder(ApplicationConfig.class)
		    .initializers(new POCApplicationContextInitializer())
		    .run(args);
		//SpringApplication.run(ApplicationConfig.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(CommonControllerConfig.class);
		EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");
	 }	 
	
	@Bean
    public  ViewResolver internalResourceViewResolver()
    {
		InternalResourceViewResolver  res = new InternalResourceViewResolver();        
        res.setPrefix("/WEB-INF/pages/");
        res.setSuffix(".jsp");
        return res;
    } 
	
	   @Bean
	    public EhCacheCacheManager cacheManager() {
	        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	    }

	    @Bean
	    public EhCacheManagerFactoryBean ehCacheCacheManager() {
	        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
	        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
	        cmfb.setShared(true);
	        return cmfb;
	    }
	  
	 
	    /**
		 * Gets the entity manager factory.
		 *
		 * @return the entityManagerFactory
		 */
		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
			entityManagerFactory.setDataSource(dataSource);
			entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
			entityManagerFactory.setPackagesToScan("com.entity");
			Properties properties = new Properties();
			properties.put("hibernate.dialect", getPropertyValue(HIBERNATE_DIALECT));
			properties.put("hibernate.show_sql", getPropertyValue(HIBERNATE_SHOW_SQL));
			entityManagerFactory.setJpaProperties(properties);
			return entityManagerFactory;
		}
		
		@Bean
		public PlatformTransactionManager jpaTransactionManager() {
			return new JpaTransactionManager(this.entityManagerFactory().getObject());
		}
		
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
		
		/**
		 * Gets the property value.
		 *
		 * @param key
		 *            the key of the property for which values is required
		 * @return the property value
		 */
		public String getPropertyValue(String key) {
			return environment.getProperty(key);
		}
	
	
}