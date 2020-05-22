package com.paymybuddy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.dao.AccountRepository;
import com.paymybuddy.dao.UserRepository;
import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.BankAccount;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.User;

@Service
public class UserServiceImp implements UserService {
	
    public static final double OVERDRAFT = 400;
    public static final double SOLDE = 0;
    public static final double MIN = 1000;
    public static final double MAX = 2000;


	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public void saveUser(User user) {
		List <Account> listAccounts = new ArrayList<Account>();
		String email = user.getEmail();
		checkUserAlreadyExist(email);
		user.setPassword(encoder.encode(user.getPassword()));
		PMBAccount pmbAccount = createPMBAccountForNewUser(user);
		BankAccount bankAccount = createBankAccountForNewUser(user);
		listAccounts.add(pmbAccount);
		listAccounts.add(bankAccount);
		user.setAccounts(listAccounts);
		userRepository.save(user);
	}

	@Override
	public void checkUserAlreadyExist(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			throw new RuntimeException("Email already exist!");
		}
	}

	@Override
	public PMBAccount createPMBAccountForNewUser(User user) {
		PMBAccount pmbAccount = new PMBAccount();
		pmbAccount.setOverdraft(OVERDRAFT);
		pmbAccount.setSolde(SOLDE);
		pmbAccount.setDateCreation(new Date());
		pmbAccount.setUser(user);
//		accountRepository.save(pmbAccount);
		return pmbAccount;
	}
	
	@Override
	public BankAccount createBankAccountForNewUser(User user) {
		BankAccount bankAccount = new BankAccount();
		double solde = generateRandomSolde();
		bankAccount.setSolde(solde);
		bankAccount.setDateCreation(new Date());
		bankAccount.setUser(user);
//		accountRepository.save(bankAccount);
		return bankAccount;
	}
	
	public double generateRandomSolde() {
		double randomValue = ThreadLocalRandom.current().nextDouble(MIN, MAX);
		double solde = DoubleRounder.round(randomValue, 2);
		return solde;
	}
	
}
