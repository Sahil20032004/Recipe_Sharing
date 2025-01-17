package com.recipeSharing.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipeSharing.module.Recipe;
import com.recipeSharing.module.User;
import com.recipeSharing.repository.RecipeRepository;

@Service //because it contains the business logic
public class recipeServiceImplementation implements recipeService {

	@Autowired
	RecipeRepository recipeRepo;
	
	@Override
	public Recipe createRecipe(Recipe recipe, User user) {
		Recipe createdrecipe = new Recipe();
		
		createdrecipe.setId(recipe.getId());
		createdrecipe.setImage(recipe.getImage());
		createdrecipe.setTitle(recipe.getTitle());
		createdrecipe.setDescription(recipe.getDescription());
		createdrecipe.setUser(user);
		createdrecipe.setCreatedAt(LocalDateTime.now());
		createdrecipe.setVegitarian(recipe.isVegitarian());
		
		return recipeRepo.save(createdrecipe);
	}

	@Override
	public Recipe FindById(Long id) throws Exception {
	
		Optional<Recipe> opt = recipeRepo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new Exception("Recipe not found");
	}

	@Override
	public void deleteRecipe(Long id) throws Exception {
		FindById(id);
		recipeRepo.deleteById(id);
	}

	@Override
	public Recipe updateRecipe(Recipe recipe, Long id) throws Exception {
		Recipe oldrecipe = FindById(id);
		if(recipe.getTitle()!=null) {
			oldrecipe.setTitle(recipe.getTitle());
		}
		if(recipe.getDescription()!=null) {
			oldrecipe.setDescription(recipe.getDescription());
		}
		if(recipe.getImage()!=null) {
			oldrecipe.setImage(recipe.getImage());
		}
		return recipeRepo.save(oldrecipe);
	}

	@Override
	public List<Recipe> findAllRecipe() {
		return recipeRepo.findAll();
	}

	@Override
	public Recipe likesDone(long recipeId, User user) throws Exception {
		Recipe likes = FindById(recipeId);
		if(likes.getLikes().contains(user.getId())) {
			likes.getLikes().remove(user.getId());
		}
		else {
			likes.getLikes().add(user.getId());
		}
		return recipeRepo.save(likes);
	}

}
