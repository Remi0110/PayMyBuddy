package com.paymybuddy.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;

	   @Override
	    protected void configure(HttpSecurity http) throws Exception {
	       http
	       .authorizeRequests()
	       .antMatchers("/login", "/registration").permitAll().antMatchers("/register").permitAll()
	       .anyRequest()
	       .authenticated()
	       .and().formLogin()   
	       .loginPage("/login")
	       .usernameParameter("email")
	       .defaultSuccessUrl("/consultAccount").permitAll()
	       .and()// logout
	       .logout().deleteCookies("JSESSIONID")
	       .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	       ;
	   }

@Override
protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
	authManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
}

@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
@Override
public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
}

@Override
public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
}


}