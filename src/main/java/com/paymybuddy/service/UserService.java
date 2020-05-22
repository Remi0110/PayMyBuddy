package com.paymybuddy.service;

import com.paymybuddy.entities.BankAccount;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.User;

public interface UserService {

	public void saveUser(User user);
	
	public void checkUserAlreadyExist(String email);
	
	public PMBAccount createPMBAccountForNewUser(User user);
	
	public BankAccount createBankAccountForNewUser(User user);
}
