package com.recipeSharing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.recipeSharing.module.User;
import com.recipeSharing.repository.UserRepository;
import com.recipeSharing.service.UserService;

@RestController
public class UserController {
 
	@Autowired
	UserService userService;
	
	@GetMapping("/api/users/profile")
	public User findByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findByJwt(jwt);
		return user;
	}
	
}
