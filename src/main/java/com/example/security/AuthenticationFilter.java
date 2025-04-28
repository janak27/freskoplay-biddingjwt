package com.example.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.SecurityContextDsl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.model.UserModel;
import com.example.service.LoginService;
import com.example.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String header =  request.getHeader("Authorization");
		String username  =  null;
		String token =  null;
		
		if(header != null && header.startsWith("Bearer ")) {
			token   =  header.substring(7);
			username =  jwtUtil.getUsernameFromToken(token);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserModel user  =  userService.getUserByUsername(username);
			UserDetails userDetails =  loginService.loadUserByUsername(user.getEmail());
			
			if(jwtUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken  =  new 
						UsernamePasswordAuthenticationToken(userDetails,null , userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
