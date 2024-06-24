package com.recipeSharing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipeSharing.config.JWProvider;
import com.recipeSharing.module.User;
import com.recipeSharing.repository.UserRepository;

@Service
public class UserServiceImplementatoin implements UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	JWProvider jwtProvider;
	
	@Override
	public User findByUserId(Long id) throws Exception {
		Optional<User> opt = userRepo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new Exception("User not Found " + id);
	}

	@Override
	public User findByJwt(String jwt) throws Exception {
		String email = jwtProvider.getEmailbyJwt(jwt);
		if(email == null) {
			throw new Exception("Invalid Token...");
		}
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new Exception("User not found ");
		}
		return user;
	}

}
