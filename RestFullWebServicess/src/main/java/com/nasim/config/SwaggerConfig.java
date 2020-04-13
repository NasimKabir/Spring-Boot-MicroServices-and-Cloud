package com.nasim.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	 public static final Contact DEFAULT_CONTACT = new Contact("Nasim Kabir", "www.nasim.com", "nasim@gmail.com");
	 
	  public static final ApiInfo DEFAULT = new ApiInfo("Awesome Api Title", "Awesome Api Documentation", "1.0", "urn:tos",
	          DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
	  
	public static  Set<String> DEFAULT_APPLICATION_CONSUMES=new HashSet<>(Arrays.asList("application/json","application/xml"));
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2).
				        apiInfo(DEFAULT).consumes(DEFAULT_APPLICATION_CONSUMES)
				        .produces(DEFAULT_APPLICATION_CONSUMES);
	}
}