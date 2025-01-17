package com.recipeSharing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipeSharing.module.User;
import com.recipeSharing.repository.UserRepository;
import com.recipeSharing.request.LoginRequest;
import com.recipeSharing.response.AuthResponse;
import com.recipeSharing.service.CustomUserService;
import com.recipeSharing.config.JWProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {
 
//setting up endpoints ofr singup and login
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	JWProvider jwtProvider;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	CustomUserService customUser;
	
	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception{
		
		String email = user.getEmail();
		String password = user.getPassword();
		String name = user.getName();
		
		User isExist = userRepo.findByEmail(email);
		if(isExist!=null) {
			throw new Exception("User already registered");
		}
		
		User createdUser = new User();
		
		createdUser.setEmail(email);
		createdUser.setName(name);
		createdUser.setPassword(passwordEncoder.encode(password));
		
		User saveUser = userRepo.save(createdUser);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String token = jwtProvider.generateToken(auth);
		AuthResponse res = new AuthResponse();
		res.setJwt(token);
		res.setMessage("User created");
		
		return res;
	}
	@PostMapping("/signin")
	public AuthResponse loginRequest(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication auth = authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String token = jwtProvider.generateToken(auth);
		AuthResponse res = new AuthResponse();
		res.setJwt(token);
		res.setMessage("Loged in complete");
		
		return res;
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUser.loadUserByUsername(username);
		if(userDetails == null) {
			throw new BadCredentialsException("User not found");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Credentials not correct");
		}
		
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
}
