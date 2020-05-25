package com.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	public User findByEmail(String username);
	
	public User findByCode(Long codeUser);
}
