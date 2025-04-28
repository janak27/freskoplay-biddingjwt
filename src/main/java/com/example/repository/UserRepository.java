package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.UserModel;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

	UserModel getUserByEmail(String email);

	UserModel getUserByUsername(String username);

}
