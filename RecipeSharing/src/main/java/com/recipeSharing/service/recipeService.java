package com.recipeSharing.service;

import java.util.List;

import com.recipeSharing.module.Recipe;
import com.recipeSharing.module.User;

public interface recipeService {

	public Recipe createRecipe(Recipe recipe,User user);
	public Recipe FindById(Long id) throws Exception;
	public void  deleteRecipe(Long id) throws Exception;
	public Recipe updateRecipe(Recipe recipe , Long id) throws Exception;
	public List<Recipe> findAllRecipe();
	public Recipe likesDone(long recipeId,User user) throws Exception;
}
