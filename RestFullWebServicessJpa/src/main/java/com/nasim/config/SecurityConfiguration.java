package com.nasim.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nasim.filter.JWTAuthenticationFilter;
import com.nasim.utility.JwtTokenProvider;

@Configuration
@EnableWebSecurity 
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	 private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
         .exceptionHandling()
         .authenticationEntryPoint(authenticationEntryPoint)
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .authorizeRequests()
         .antMatchers("/api/v1/register", "/api/v1/login")
         .permitAll()
         .anyRequest()
         .authenticated();
		 

 http
 .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
 

 
	}

}
