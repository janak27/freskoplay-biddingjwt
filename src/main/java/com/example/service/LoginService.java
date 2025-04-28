package com.example.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.UserModel;
import com.example.model.UserPrincipal;

@Service
public class LoginService implements UserDetailsService {

	/*
	 * Implement the business logic for the LoginService operations in this class
	 * Make sure to add required annotations
	 */

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserModel user = userService.getUserByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("user not found with email" + email);
		}
		
		
		//return new UserPrincipal(user);
		
		
		return User.builder().username(user.getUsername()).password(user.getPassword())
				.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRolename())))
				.build();

		// List<GrantedAuthority> authorities =
		// buildUserAuthority(user.getRole().getRolename());

		// return buildUserForAuthentication(user, authorities);
	}

//	private UserDetails buildUserForAuthentication(UserModel user, List<GrantedAuthority> authorities) {
//
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				authorities);
//	}
//
//	private List<GrantedAuthority> buildUserAuthority(String userRule) {
//		return Collections.singletonList(new SimpleGrantedAuthority(userRule));
//
//	}

}