package com.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PA") 
public class PMBAccount extends Account{
	
	private double overdraft;

	public PMBAccount() {
		super();
	}

	public PMBAccount(String codeAccount, Date dateCreation, double solde,  User user, double overdraft) {
		super(codeAccount, dateCreation, solde, user);
		this.overdraft = overdraft;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	
	
}
