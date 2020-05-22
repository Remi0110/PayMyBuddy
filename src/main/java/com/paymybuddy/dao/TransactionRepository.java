package com.paymybuddy.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	@Query("select t from Transaction t where t.fromAccount.codeAccount=:x or t.toAccount.codeAccount=:x order by t.dateTransaction desc") 
	public Page<Transaction>  listTransaction(@Param("x")String codeAccount,Pageable pageable); 
	
}
