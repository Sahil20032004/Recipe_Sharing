package com.recipeSharing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipeSharing.module.Recipe;
import com.recipeSharing.module.User;
import com.recipeSharing.service.UserService;
import com.recipeSharing.service.recipeService;

@RestController
@RequestMapping("/api/recipe/")
public class RecipeController {
 
	@Autowired
	recipeService recipeserve;
	@Autowired
	UserService userservice;
	
	@PostMapping()
	public Recipe createRecipe(@RequestBody Recipe recipe , @RequestHeader("Authorization") String jwt) throws Exception {
		User user = userservice.findByJwt(jwt);
		Recipe createdrecipe = recipeserve.createRecipe(recipe, user);
		return createdrecipe; 
	}
	@GetMapping("/")
	public List<Recipe> getAllRecipe() {
		List<Recipe> recipe = recipeserve.findAllRecipe();
		return recipe;
	}
	@DeleteMapping("/{id}")
	public String deleteRecipe(@PathVariable Long id) throws Exception {
		recipeserve.deleteRecipe(id);
		return "Recipe Deleted succesfuly";
	}
	@PutMapping("/{id}")
	public Recipe updateRecipe(@RequestBody Recipe recipe , @PathVariable Long id) throws Exception {
		Recipe toupdate = recipeserve.updateRecipe(recipe, id);
		return toupdate;
	}
	@PutMapping("/like/{id}")
	public Recipe likescount(@PathVariable Long id , @RequestHeader("Authorization") String jwt) throws Exception {
		User user = userservice.findByJwt(jwt);
		Recipe countlike = recipeserve.likesDone(id, user);
		return countlike;
	}
	
	
}
