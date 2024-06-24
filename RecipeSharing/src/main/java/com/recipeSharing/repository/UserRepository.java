package com.recipeSharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipeSharing.module.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);
}
