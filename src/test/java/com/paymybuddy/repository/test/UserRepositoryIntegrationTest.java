package com.paymybuddy.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.dao.UserRepository;
import com.paymybuddy.entities.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DirtiesContext(classMode =DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTest {

	  
	  @Autowired
	    private UserRepository userRepository;

	  
	  @Test
	  public void testFindByEmail() {
	      User user = new User("ZZZZZZ","YYYYYY","SSSSSSS@gmail.com","1234");
	      userRepository.save(user);
	      User user2 = userRepository.findByEmail("SSSSSSS@gmail.com");
	      assertNotNull(user);
	      assertEquals(user.getLastName(), user2.getLastName());
	      assertEquals(user.getFirstName(), user2.getFirstName());
	      assertEquals(user.getEmail(), user2.getEmail());
	  }
	  
	  @Test
	  public void testFindByCode() {
	      User user = new User("YYYYY", "ZZZZZZ", "XXXXXXX@gmail.com","1234");
	      userRepository.save(user);
	      Long code = 1L;
	      User user2 = userRepository.findByCode(code);
	      assertNotNull(user2);
	      assertEquals(user.getFirstName(), user2.getFirstName());
	  }
	  
	  @Test
	  public void testAddFriends() {
	      User user = new User("YYYYY", "ZZZZZZ", "XXXXXXX@gmail.com","1234");
	      userRepository.save(user);
	      User user2 = new User("AAAAAA", "SSSSSS", "WWWWWWW@gmail.com","1234");
	      userRepository.save(user2);
	      User user3 = new User("EEEEEE", "IIIIII", "LLLLLLL@gmail.com","1234");
	      userRepository.save(user3);
	      user = userRepository.findByEmail("XXXXXXX@gmail.com");
	      assertNull(user.getFriends());
	      
	      user.setFriends(new LinkedList<>(Arrays.asList(user2, user3)));
	      userRepository.save(user);
	      user = userRepository.findByEmail("XXXXXXX@gmail.com");
	      assertNotNull(user.getFriends());
		  assertEquals(2, user.getFriends().size());

	  }
	
}
