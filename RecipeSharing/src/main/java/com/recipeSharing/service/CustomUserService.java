package com.recipeSharing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.recipeSharing.module.User;
import com.recipeSharing.repository.UserRepository;

@Service
public class CustomUserService implements UserDetailsService {

	//Service used to stop generating password for spring security
	@Autowired
	 UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		List<GrantedAuthority> auth = new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),auth);
	}

}
