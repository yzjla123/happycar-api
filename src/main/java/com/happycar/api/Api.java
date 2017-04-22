package com.happycar.api;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;

import com.happycar.api.utils.SpringContextUtil;

/**
 * Hello world!
 *
 */
@Configuration
@RestController
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.happycar.api")
public class Api 
{
    public static void main( String[] args )
    {
    	final ApplicationContext applicationContext = SpringApplication.run(Api.class, args);
    	SpringContextUtil.setApplicationContext(applicationContext);
    }
    
    
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        factory.setMaxFileSize("3072KB");  
        factory.setMaxRequestSize("3072KB");  
        return factory.createMultipartConfig();  
    }  
   
}
