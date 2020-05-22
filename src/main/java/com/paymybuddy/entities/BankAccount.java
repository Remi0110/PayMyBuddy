package com.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BA") 
public class BankAccount extends Account{
	

	public BankAccount() {
		super();
	}

	public BankAccount(String codeAccount, Date dateCreation, double solde,  User user) {
		super(codeAccount, dateCreation, solde, user);
	}


}
