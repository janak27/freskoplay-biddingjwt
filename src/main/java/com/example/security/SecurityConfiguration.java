package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.service.LoginService;

//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.context.annotation Bean:
//import org.springframework.context annotation.Configuration;
//import org.springframework.http.HttpStatus:
//import org.springframework.security.authentication. AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders. AuthenticationManage 
//import org.springframework.security.config.annotation.method.configuration. EnableGlobal Method Secur
//import org.springframework, security.config.annotation.web.builders.HttpSecurity: 
//import org.springframework.security.config.annotation.web.builders.WebSecurity:
//import org.springframework.security.config.annotation.web.configuration. EnableWebSecurity:
//import org.springframework.security.config, annotation.web.configuration,WebSecurityConfigurerAdapte 
//import org.springframework.security.config.http. SessionCreationPolicy:
//import org.springframework.security.crypto.password. HoopPasswordEncoder;
//import org.springframework.security.crypto.password. PasswordEncoder:
//import org.springframework.security.web.authentication.HttpStatus EntryPoint;
//import org.springframework, security.web.authentication. Username PasswordAuthenticationFilter; 


//public class SecurityConfiguration extends WebSecurityConfigurerAdapter { //WebSecurityConfigurerAdapter is removed from jdk 21

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	/*Configure the necessary authentication processes in this class
	Override the configure method and define the authentication parameters*/
	
//	@Override
//	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
//	auth.userDetailsService(loginService);
//	}
//	
//	@Override
//	public void configure (WebSecurity web) throws Exception {
//	web.ignoring().antMatchers("/h2-console/**")
//	antMatchers("/login");
//	
//	}
//	
//		
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//
//		}
//
//		@Bean
//		public PasswordEncoder passwordEncoder() {
//
//		return null;
//
//		}
//
//		@Override
//		@Bean
//		protected AuthenticationManager authenticationManager() throws Exception {
//		return super.authenticationManager();
//		}
	
	
	@Autowired
	private LoginService loginService; 
	
	@Autowired
	private AuthenticationFilter authFilter; // filter
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		//making security chain
		
		http
		.csrf(csrf -> csrf.disable())
		.cors(cors -> cors.disable())
		.authorizeHttpRequests(request -> request.requestMatchers("/login").permitAll()
				.requestMatchers("/bidding/add").hasAuthority("BIDDER")
				.requestMatchers("/bidding/list" , "/bidding/delete/**").hasAnyAuthority("BIDDER"  ,"APPROVER")
				.requestMatchers("/bidding/update/**").hasAuthority("APPROVER")
				.anyRequest().authenticated())
		.sessionManagement(session ->  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
		.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();		
		
	}
	
	// create this auth dao provider for internal chaining
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(loginService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
	}
	
	// to finalize the configuration of auth manager
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	
	
}
