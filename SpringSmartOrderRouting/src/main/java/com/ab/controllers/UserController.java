package com.ab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ab.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
}
