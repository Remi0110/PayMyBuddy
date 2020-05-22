package com.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	
	public User findByEmail(String username);
	
//	@Query("select u from User u, Account a where a.codeAccount = :x in elements(u.accounts)") 
//	public User findUserByCodeAccount(@Param("x")String codeAccount);
	public User findByCode(Long codeUser);
}
