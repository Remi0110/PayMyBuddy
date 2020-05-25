package com.paymybuddy.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.BankAccount;
import com.paymybuddy.entities.PMBAccount;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.User;
import com.paymybuddy.service.BankService;
import com.paymybuddy.service.SecurityService;
import com.paymybuddy.service.UserService;

@Controller
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value="/login")
	 public String login() { 
		 return "login";
	 }

	@RequestMapping("/account")  
	public String index() {   
		 return "account"; 
	 }
	
	@RequestMapping("/registration")  
	public String registration(Model model) {   
		User user = new User();
        model.addAttribute("user", user);
		return "registration";
		}
	
	@RequestMapping("/welcome")  
	public String welcome() {   
		 return "welcome"; 
		}
	

	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerUser(@Valid User user, BindingResult bindingResult, RedirectAttributes RedirectAttributes) {
	    String originalPassword = user.getPassword();

		if(bindingResult.hasErrors()) return "registration";
		try {
			userService.saveUser(user);
	        securityService.autoLogin(user.getEmail(), originalPassword);

		} catch (Exception e) {
			RedirectAttributes.addFlashAttribute("exception", e.getMessage());		
			return "redirect:/registration";  
		}
		return "redirect:/welcome";	
		}
	
	
	@RequestMapping("/consultAccount")  
	public String consultAccount(Model model, String codeAccount,@RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="5")int size) {   
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username =auth.getName();
		User user = bankService.findUserByEmail(username);
		Collection<Account> listAccounts = user.getAccounts();
		

		try {	
			Account pmbAccount = bankService.getPMBAccountFromListOfAccounts(listAccounts);
			Page<Transaction>  pageTransaction = bankService.listTransactionAccount(pmbAccount.getCodeAccount() ,page, size);
			Collection<User> listFriends = user.getFriends();
			model.addAttribute("listTransaction",pageTransaction.getContent());
			int[] pages=new int[pageTransaction.getTotalPages()];
			model.addAttribute("codeAccount",pmbAccount.getCodeAccount());
			model.addAttribute("listFriends", listFriends);
			model.addAttribute("pages", pages);
			model.addAttribute("account",pmbAccount);
			model.addAttribute("user",user.getCode());

	 
		}catch (Exception e) {
			model.addAttribute("exception",e);
		}
		

		return "account";
	}

	@RequestMapping(value="/saveTransaction",method=RequestMethod.POST)   
	public String saveOperation(Model model, String codeAccount, String amount, Long friendCode, String description, RedirectAttributes RedirectAttributes) {   
		
		try {
		
			String codeAccount2= bankService.getCodePMBAccountFromUserCode(friendCode);
			bankService.virement(codeAccount,codeAccount2,description,amount);
			
		}catch (Exception e) {
			RedirectAttributes.addFlashAttribute("exception", e.getMessage());

		}
		 
		return "redirect:/consultAccount?codeAccount="+codeAccount;  
	}
	
	@RequestMapping(value="/addConnections",method=RequestMethod.POST)  
	public String addConnections(Model model, Long codeUser, String recipient,  String codeAccount, RedirectAttributes RedirectAttributes) {
		try {
		User userToAdd = bankService.findUserByEmail(recipient);
		User userReceiving = bankService.findUserByCode(codeUser);
		bankService.addCoonection(userToAdd, userReceiving);
		RedirectAttributes.addFlashAttribute("message", "You have a new buddy! Start Transactions!");
		}catch (Exception e) {
			RedirectAttributes.addFlashAttribute("exception", e.getMessage());

		}

		return "redirect:/consultAccount?codeAccount="+codeAccount; 
	}
	
	
	@RequestMapping("/profil")  
	public String profil(Model model) {   
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username =auth.getName();
		try {	
			User user = bankService.findUserByEmail(username);
			Collection<Account> listAccounts = user.getAccounts();
			Account pmbAccount = bankService.getPMBAccountFromListOfAccounts(listAccounts);
			model.addAttribute("code",pmbAccount);

		}catch (Exception e) {
			model.addAttribute("exception",e);
		}
		 return "profil"; 
	 }
	
	@RequestMapping("/transfer")  
	public String transfer(Model model) {   
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username =auth.getName();
		User user = bankService.findUserByEmail(username);
		Collection<Account> listAccounts = user.getAccounts();
		try {	
			Account pmbAccount = bankService.getPMBAccountFromListOfAccounts(listAccounts);
			Account bankAccount = bankService.getBankAccountFromListOfAccounts(listAccounts);
			model.addAttribute("pmbAccount",pmbAccount.getCodeAccount());
			model.addAttribute("bankAccount",bankAccount.getCodeAccount());

		}catch (Exception e) {
			model.addAttribute("exception",e);
		}
		 return "transfer"; 
	 }
	
	@RequestMapping(value="/transferFromPMBToBank",method=RequestMethod.POST)  	
	public String transferFromPMBToBank(Model model, String amount, String pmbAccount, String bankAccount, RedirectAttributes RedirectAttributes) {
		try {
		bankService.transferMoneyFromPMBAccountToBankAccount(amount, pmbAccount, bankAccount);
		RedirectAttributes.addFlashAttribute("message", "Transfer succesfully completed!");
		}catch (Exception e) {
			RedirectAttributes.addFlashAttribute("exception", e.getMessage());
		}
		return "redirect:/transfer"; 
	 }
	
	@RequestMapping(value="/transferFromBankToPMB",method=RequestMethod.POST)  	
	public String transferFromBankToPMB(Model model, String amount, String pmbAccount, String bankAccount, RedirectAttributes RedirectAttributes) {
		try {
		bankService.transferMoneyFromBankAccountToPMBAccount(amount, pmbAccount, bankAccount);
		RedirectAttributes.addFlashAttribute("message", "Transfer succesfully completed!");
		}catch (Exception e) {
			RedirectAttributes.addFlashAttribute("exception", e.getMessage());
		}
		return "redirect:/transfer"; 
	 }
	
}
