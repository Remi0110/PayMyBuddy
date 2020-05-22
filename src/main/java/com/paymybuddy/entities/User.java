package com.paymybuddy.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class User implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="USER_ID")
	private Long code;
	
	@NotEmpty(message="Last name is required")
	private String lastName;
	
	@NotEmpty(message="First name is required")
	private String firstName;
	
	@NotEmpty(message="Email is required")
	private String email;
	
	@Length(min=4, message="Password should be at least 4 characters")
	private String password;
	
	@OneToMany(mappedBy="user" ,fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Collection<Account> accounts;
	
	@JoinTable(name = "USER_FRIENDS", joinColumns = {
			  @JoinColumn(name = "ADDING_USER", referencedColumnName = "USER_ID", nullable =   false)}, inverseJoinColumns = {
			  @JoinColumn(name = "ADDED_USER", referencedColumnName = "USER_ID", nullable = false)})
			  @ManyToMany
	private Collection<User> friends;

    @ManyToMany(mappedBy = "friends")
    private Collection<User> addUser;
		
	public User() {
		super();
	}

	public User(String lastName, String firstName, String email, String password) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}


	public Collection<User> getFriends() {
		return friends;
	}

	public void setFriends(Collection<User> friends) {
		this.friends = friends;
	}


	
	
	
}
