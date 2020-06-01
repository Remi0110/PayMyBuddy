package com.paymybuddy.service.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.dao.AccountRepository;
import com.paymybuddy.dao.TransactionRepository;
import com.paymybuddy.dao.UserRepository;
import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.User;
import com.paymybuddy.service.BankService;
import com.paymybuddy.service.BankServiceImpl;


@ExtendWith(SpringExtension.class)
public class BankServiceImplTest {

	@TestConfiguration
    static class BankServiceImplTestContextConfiguration {
  
        @Bean
        public BankService bankService() {
            return new BankServiceImpl();
        }
    }
	
	@Autowired
    private BankService bankService;
	
	
	@MockBean
	private static AccountRepository accountRepository;
	
	@MockBean
	private static UserRepository userRepository;
	
	@MockBean
	private static TransactionRepository transactionRepository;

	
	@Test
	public void testVirement() {
	    User user = new User("ZZZZZZ","YYYYYY","SSSSSSS@gmail.com","1234");
	    User user2 = new User("SSSSSS","AAAAAA","WWWWWWW@gmail.com","1234");
	    Account account = new PMBAccount("a1", new Date(), 0, user, 400);
	    Account account2 = new PMBAccount("a2", new Date(), 0, user2, 400);
	    double amount = 100;
	    double amount2 = -100.5;
		Transaction transaction = new Transaction(new Date(), "Virement", 100, account, account2);

	    Mockito.when(accountRepository.findById(account.getCodeAccount()))
	      .thenReturn(Optional.of(account));
	    Mockito.when(accountRepository.findById(account2.getCodeAccount()))
	      .thenReturn(Optional.of(account2));
	    Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
	    Mockito.when(accountRepository.save(account)).thenReturn(account);

		bankService.virement("a1", "a2", "virement", "100");
		 assertThat(account.getSolde())
	      .isEqualTo(amount2);
		 assertThat(account2.getSolde())
	      .isEqualTo(amount);
	 }
	
}
