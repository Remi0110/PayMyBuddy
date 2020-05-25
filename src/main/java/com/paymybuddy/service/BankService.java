package com.paymybuddy.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.User;

public interface BankService {

	 public Account getAccount(String codeAccount);
	 public void retrait(String codeAccount, double amount);
	 public void virement(String codeAccountRemove,String codeAccountPayment,String description,String amount);
	 public Page<Transaction> listTransactionAccount(String codeAccount,int page,int sizePage);
	 public User findUserByEmail(String email);
	 public Account getPMBAccountFromListOfAccounts(Collection<Account>listAccounts);
	 public Account getBankAccountFromListOfAccounts(Collection<Account>listAccounts);
	 public User findUserByCode(Long codeUser);
	 public void addCoonection(User userToAdd, User userReceiving);
	 public String getCodePMBAccountFromUserCode(Long codeUser);
	 public void transferMoneyFromPMBAccountToBankAccount (String amount, String pmbAccount, String bankAccount);
	 public void transferMoneyFromBankAccountToPMBAccount (String amount, String pmbAccount, String bankAccount);
	 public double taxPourcentage(double amount);
	 public double convertStringToDouble(String amount);
	 
	 }
