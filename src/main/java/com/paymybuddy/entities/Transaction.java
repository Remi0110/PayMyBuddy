package com.paymybuddy.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Transaction implements Serializable{
	
	@Id @GeneratedValue
	private Long number;
	private Date dateTransaction;
	private String description;
	private double amount;
	@ManyToOne
	@JoinColumn(name="CODE_ACCOUNT2")
	private Account toAccount;
	@ManyToOne
	@JoinColumn(name="CODE_ACCOUNT")
	private Account fromAccount;
	
	public Transaction() {
		super();
	}

	public Transaction(Date dateTransaction, String description, double amount, Account fromAccount, Account toAccount) {
		super();
		this.dateTransaction = dateTransaction;
		this.description = description;
		this.amount = amount;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	
}
