package com.paymybuddy.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_ACCOUNT",discriminatorType=DiscriminatorType.STRING,length=2)
public abstract class Account implements Serializable{
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String codeAccount;
	private Date dateCreation;
	private double solde;
	@ManyToOne
	@JoinColumn(name="CODE_USER")
	private User user;
	@OneToMany(mappedBy="fromAccount" ,fetch=FetchType.LAZY)
	private Collection <Transaction> payers;
	@OneToMany(mappedBy="toAccount" ,fetch=FetchType.LAZY)
	private Collection <Transaction> receivers;
	
	public Account() {
		super();
	}

	public Account(String codeAccount, Date dateCreation, double solde, User user) {
		super();
		this.codeAccount = codeAccount;
		this.dateCreation = dateCreation;
		this.solde = solde;
		this.user = user;
	}

	public String getCodeAccount() {
		return codeAccount;
	}

	public void setCodeAccount(String codeAccount) {
		this.codeAccount = codeAccount;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Transaction> getPayers() {
		return payers;
	}

	public void setPayers(Collection<Transaction> payers) {
		this.payers = payers;
	}

	public Collection<Transaction> getReceivers() {
		return receivers;
	}

	public void setReceivers(Collection<Transaction> receivers) {
		this.receivers = receivers;
	}

	
	
	
	
}
