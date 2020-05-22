package com.paymybuddy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.Account;


public interface AccountRepository extends JpaRepository<Account, String>{


}
