package com.nasim.config;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nasim.jwt.JwtConfig;
import com.nasim.jwt.JwtTokenVerifier;
import com.nasim.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.nasim.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled  = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    
    @Autowired
	public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder,
			JwtConfig jwtConfig, SecretKey secretKey) {
		super();
		this.customUserDetailsService = customUserDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

	  
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
		 http
		 .cors()
         .and()
		 .csrf().disable()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
         .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
         .authorizeRequests()
         .antMatchers("/login").permitAll()
         .antMatchers("/api/v1/**").permitAll()
         .antMatchers(HttpMethod.OPTIONS, "/login").permitAll()//allow CORS option calls
         .antMatchers(HttpMethod.POST, "/login").permitAll()
         .anyRequest()
         .authenticated();
		 
		 http
         .headers()
         .frameOptions().sameOrigin()  //H2 Console Needs this setting
         .cacheControl();
		 
	       
	    }

	 
}
