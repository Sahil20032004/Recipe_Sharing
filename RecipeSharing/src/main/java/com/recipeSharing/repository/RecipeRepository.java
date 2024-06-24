package com.recipeSharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipeSharing.module.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{

}
