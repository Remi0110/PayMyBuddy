package com.paymybuddy.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
 
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {  
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/registration").setViewName("registration");
		registry.addViewController("/account").setViewName("account");
		registry.addViewController("/profil").setViewName("profil");
		registry.addViewController("/transfer").setViewName("transfer");

	}
}