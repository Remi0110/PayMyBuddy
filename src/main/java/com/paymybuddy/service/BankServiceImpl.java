package com.paymybuddy.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.dao.AccountRepository;
import com.paymybuddy.dao.TransactionRepository;
import com.paymybuddy.dao.UserRepository;
import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.BankAccount;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.User;

@Service
@Transactional
public class BankServiceImpl implements BankService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Account getAccount(String codeAccount) {
		Account account = accountRepository.findById(codeAccount).get();
		if (account == null)
			throw new RuntimeException("Account not found");
		return account;
	}

	@Override
	public void retrait(String codeAccount, double amount) {
		Account account = getAccount(codeAccount);
		double facilitesCaisse = 0;

		if (account instanceof PMBAccount) {

			facilitesCaisse = ((PMBAccount) account).getOverdraft();

			if (account.getSolde() + facilitesCaisse < amount)
				throw new RuntimeException("Insufficient balance!");

		}

		// mettre a jour le solde du compte
		account.setSolde(account.getSolde() - amount);
		accountRepository.save(account);
	}

	@Override
	public void virement(String codeAccountRemove, String codeAccountPayment, String description, String amount) {
		if (codeAccountRemove == codeAccountPayment)
			throw new RuntimeException("Impossible : You cannot make a transfer in the same account");
		double amountConverted = convertStringToDouble(amount);
		Account fromAccount = getAccount(codeAccountRemove);
		Account toAccount = getAccount(codeAccountPayment);
		Transaction transaction = new Transaction(new Date(), description, amountConverted, fromAccount, toAccount);
		transactionRepository.save(transaction);
		double tax = taxPourcentage(amountConverted);

		retrait(codeAccountRemove, amountConverted + tax);
		toAccount.setSolde(toAccount.getSolde() + amountConverted);
		accountRepository.save(toAccount);

	}

	@Override
	public Page<Transaction> listTransactionAccount(String codeAccount, int page, int sizePage) {
		return transactionRepository.listTransaction(codeAccount, PageRequest.of(page, sizePage));
	}

	@Transactional(readOnly = true)
	@Override
	public User findUserByEmail(String recipient) {
		User user = userRepository.findByEmail(recipient);
		if (user == null) {
			throw new RuntimeException("this user is not registered on the app!");

		}

		return user;
	}

	public Account getPMBAccountFromListOfAccounts(Collection<Account> listAccounts) {
		Account pmbAccount = null;
		try {
			for (Account account : listAccounts) {
				if (account.getClass().equals(PMBAccount.class)) {
					pmbAccount = account;
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return pmbAccount;
	}

	public Account getBankAccountFromListOfAccounts(Collection<Account> listAccounts) {
		Account bankAccount = null;
		try {
			for (Account account : listAccounts) {
				if (account.getClass().equals(BankAccount.class)) {
					bankAccount = account;
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return bankAccount;
	}

	@Override
	public User findUserByCode(Long codeUser) {
		User user = null;
		try {
			user = userRepository.findByCode(codeUser);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	@Override
	public void addCoonection(User userToAdd, User userReceiving) {
		if (userReceiving.getFriends().contains(userToAdd)) {
			throw new RuntimeException("this buddy is already in your friends!");
		} else if (userToAdd.getEmail().equalsIgnoreCase(userReceiving.getEmail())) {
			throw new RuntimeException("You can't do a transaction with yourself!");
		} else {
			userReceiving.getFriends().add(userToAdd);
		}
	}

	public String getCodePMBAccountFromUserCode(Long codeUser) {
		User user = findUserByCode(codeUser);
		Account account = getPMBAccountFromListOfAccounts(user.getAccounts());
		return account.getCodeAccount();
	}
	
	@Override
	public void transferMoneyFromPMBAccountToBankAccount (String amount, String pmbAccount, String bankAccount) {
		double amountConverted = convertStringToDouble(amount);
		double tax = taxPourcentage(amountConverted);
		retrait(pmbAccount, amountConverted + tax);

		Account toAccount = getAccount(bankAccount);
		toAccount.setSolde(toAccount.getSolde() + amountConverted);
		accountRepository.save(toAccount);

	}

	@Override
	public void transferMoneyFromBankAccountToPMBAccount (String amount, String pmbAccount, String bankAccount) {
		double amountConverted = convertStringToDouble(amount);
		double tax = taxPourcentage(amountConverted);
		retrait(bankAccount, amountConverted + tax);

		Account toAccount = getAccount(pmbAccount);
		toAccount.setSolde(toAccount.getSolde() + amountConverted);
		accountRepository.save(toAccount);
	}
	
	@Override
	public double taxPourcentage(double amount) {
		double tax = (amount * 0.5) / 100;
		return tax;
	}
	
	public double convertStringToDouble(String amount) {
		double result = 0;
		try {
			result = Double.parseDouble(amount);
		} catch (Exception e) {
			throw new RuntimeException("Invalid Input! Please enter a valid amount.");
		}
		return result;
	}
}
