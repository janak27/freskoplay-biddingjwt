package com.example.controller;

//package com.fresco tenderManagement.controller;
//
//import com.fresco.tenderManagement.dto.LoginDTO;
//
//import com.fresco.tenderManagement.security.JWTUtil;
//import com.fresco.tenderManagement service.LoginService;
//import org.springframework.beans.factory.annotation. Autowired:
//import org.springframework.http.HttpStatus;
//import org.springframework.http. ResponseEntity:
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security authentication.BadCredentialsException;
//import org.springframework.security.authentication.Username PasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails:
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation,RequestBody:
//import org.springframework.web.bind.annotation RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginDTO;
import com.example.security.JWTUtil;
import com.example.service.LoginService;

@RestController
public class LoginController {

	/*
	 * This controller would be responsible for the login endpoints Add the required
	 * annotations and create the endpoints
	 */
	@Autowired
	private AuthenticationManager authenticationManager; //created in security configuration

	@Autowired
	LoginService loginService;// created in services to handle login activities

	@Autowired
	private JWTUtil jwtUtil;//created in security for handling jwt methods

	@PostMapping("/login")
		public ResponseEntity<Object> authenticateUser(@RequestBody  LoginDTO authenticationRequest) throws Exception {

			try {
			// create auth object with authmanager
			Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));
			
			//take userdetails of user to make them login, login mai loadUsernyusername create kro pahle 
			UserDetails userDetails  =  loginService.loadUserByUsername(authenticationRequest.getEmail());
			
			//agar user authenticate hua toh jwt token generate karenge
			if(auth.isAuthenticated()) {
				String token =  jwtUtil.generateToken(userDetails); 
				Map<String , Object> response  =  new HashMap<>();
				response.put("jwt", token);
				response.put("status", 200);
				return new ResponseEntity<>(response , HttpStatus.OK);
			}
			return new ResponseEntity<>("Login failed" , HttpStatus.BAD_REQUEST);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
			}
			//go directly to security package and create all 3 files there first.
		}

	@GetMapping("/hello")
	public String greet() {
		return "hii chandu";

	}

}