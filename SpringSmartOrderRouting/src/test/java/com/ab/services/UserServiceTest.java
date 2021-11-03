package com.ab.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest {
	@Autowired
	private UserService userService;
	
	int userId = 1;
	String user_first_name = "Ranul"; 
	String user_last_name = "Kumara";
	String user_email = "ranul@gmail"; 
	String password = "password123";
	int user_age = 29;
	double wallet_balance = 4000; 
	String userRegion = "EMEA";
	@Test
	public void testFindUserByUserId() {
		assertNotNull(userService.findUserByUserId(userId));
	}
	
	@Test
	public void testInsertNewUser() {
		userId = 54;
		assertEquals(1,userService.insertNewUser(userId, user_first_name, user_last_name, user_email, password, user_age, wallet_balance, userRegion));
	}
	
	@Test
	public void testVerifyUser() {
		assertNotNull(userService.verifyUser("ranul_24@outlook.com","h5B/DrX8flTDiBUhvf+i/Q=="));
	}
	
	@Test
	public void testValidateEmail() {
		assertNotNull(userService.validateEmail("ranul_24@outlook.com"));
	}
	
	@Test
	public void testFindMaxUserId() {
		assertNotEquals(0,userService.findMaxUserId());
	}
	
	@Test
	public void testFindUserBalance() {
		assertNotNull(userService.findUserBalance(userId));
	}
	
	@Test
	public void testVerifyEmail() {
		assertEquals("ranul_24@outlook.com",userService.verifyEmail("ranul_24@outlook.com"));
	}
	
	@Test
	public void testFindUserByUserEmail() {
		assertNotNull(userService.findUserByUserEmail("ranul_24@outlook.com"));
	}
	
	@Test
	public void testupdateUser() {
		assertEquals(1,userService.updateUser(userId, user_first_name, user_last_name, user_email, password, user_age, wallet_balance, userRegion));
	}
	
}
