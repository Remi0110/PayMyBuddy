package com.paymybuddy.repository.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.dao.AccountRepository;
import com.paymybuddy.dao.TransactionRepository;
import com.paymybuddy.dao.UserRepository;
import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.User;
import com.paymybuddy.service.BankService;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DirtiesContext(classMode =DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionRepositoryIntegrationTest {
	
	  @Autowired
	  private AccountRepository accountRepository;

	  @Autowired
	  private TransactionRepository transactionRepository;
	  
	  @Autowired
	  private UserRepository userRepository;
	  
	  @MockBean
	  private static BankService bankService;

	  
	  @Test
	  public void testTransaction() {
		 User u1 = new User("ZZZZZZ","YYYYYY","RRRRRRR@gmail.com","1234");
		 userRepository.save(u1);
	     Account account1 = new PMBAccount("a1", new Date(), 50000, u1, 6000);
	     accountRepository.save(account1);
	     User u2 = new User("AAAAAA","XXXXXX","EEEEEEE@gmail.com","1234");
		 userRepository.save(u2);
	     Account account2 = new PMBAccount("a2", new Date(), 50000, u1, 6000);
	     accountRepository.save(account2);
	     
	 	 transactionRepository.save(new Transaction(new Date(), "Virement", 800, account1, account2));
	 	 Long number = 1L;
	 	 Optional<Transaction> transaction = transactionRepository.findById(number);
	     assertNotNull(transaction);
	     assertEquals(transaction.get().getAmount(), 800);
	     assertEquals(transaction.get().getFromAccount().getCodeAccount(), account1.getCodeAccount());
	     assertEquals(transaction.get().getToAccount().getCodeAccount(), account2.getCodeAccount());
	     assertEquals(transaction.get().getDescription(),"Virement");
	  }

}
