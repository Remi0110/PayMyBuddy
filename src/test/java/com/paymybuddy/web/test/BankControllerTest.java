package com.paymybuddy.web.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.dao.UserRepository;
import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void registrationTest() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/register").with(csrf())
						.content(asJsonString(new User("ZZZZZZ", "YYYYYY", "SSSSSSS@gmail.com", "1234")))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void saveTransactionTest() throws Exception {
		User user = new User("ZZZZZZ", "YYYYYY", "SSSSSSS@gmail.com", "1234");
		User user2 = new User("SSSSSS", "AAAAAA", "WWWWWWW@gmail.com", "1234");
		Account account = new PMBAccount("a1", new Date(), 0, user, 400);
		Account account2 = new PMBAccount("a2", new Date(), 0, user2, 400);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/register").with(csrf())
						.content(asJsonString(new Transaction(new Date(), "Virement", 600, account, account2)))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
