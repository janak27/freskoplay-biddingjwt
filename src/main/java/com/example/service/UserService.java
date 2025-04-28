package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserModel;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;


@Service
public class UserService {

/*Implement the business logic for the UserService operations in this class Make sure to add required annotations*/

	
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private RoleRepository roleRepository;
		
		//get user by email
		
		public UserModel getUserByEmail(String email ) {
			return userRepository.getUserByEmail(email);
		
		}

		public UserModel getUserByUsername(String username) {
			// TODO Auto-generated method stub
			return userRepository.getUserByUsername(username);
		}
}