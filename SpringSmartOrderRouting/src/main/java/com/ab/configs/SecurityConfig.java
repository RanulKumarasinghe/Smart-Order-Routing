package com.ab.configs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.ab.entities.User;
import com.ab.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//private final UserService userService;
	
//	@Autowired
//	public SecurityConfig(UserService userService) {
//		this.userService = userService;
//	}
	
	
//	@Bean 
//	public UserDetailsService  userDetailsService() {
//		return (UserDetailsService) userEmail  -> {
//			Optional<User> user = userService.findUserByUserEmail(userEmail);
//			if(user.isEmpty()) {
//				System.out.println("No user found with user email:"+userEmail);
//				//throw new UserEmailNotFoundException("No user found with user email: "+userEmail);
//			}
//			return (UserDetails) user.get();
//		};
//	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//	}
	
	@Override
    protected void configure(HttpSecurity security) throws Exception
    {
	   //security.httpBasic().disable();
	    security.csrf().disable();
//	    security
//	    .authorizeRequests()
//	    .antMatchers("/","/users")
//	    .permitAll()
//	    .anyRequest()
//	    .authenticated()
//	    .and()
//	    .formLogin()
//	    .loginPage("/login")
//	    .defaultSuccessUrl("/dashboard")
//	    .permitAll()
//	    .and()
//	    .logout()
//	    .permitAll();
	    
    
    }
	
}
