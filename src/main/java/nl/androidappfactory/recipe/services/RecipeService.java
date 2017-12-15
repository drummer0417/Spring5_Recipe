package nl.androidappfactory.recipe.services;

import java.util.List;

import nl.androidappfactory.recipe.commands.RecipeCommand;
import nl.androidappfactory.recipe.models.Recipe;

public interface RecipeService {

	List<Recipe> getAllRecipes();

	Recipe findById(long l);

	RecipeCommand findCommandById(Long id);

	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

}
