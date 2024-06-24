package com.recipeSharing.service;

import com.recipeSharing.module.User;

public interface UserService {

	public User findByUserId(Long id) throws Exception;
	public User findByJwt(String jwt) throws Exception;
	
}
