package com.paymybuddy;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.paymybuddy.dao.AccountRepository;
import com.paymybuddy.dao.TransactionRepository;
import com.paymybuddy.dao.UserRepository;
import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.BankAccount;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.User;
import com.paymybuddy.service.BankService;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner{
	

	
	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
