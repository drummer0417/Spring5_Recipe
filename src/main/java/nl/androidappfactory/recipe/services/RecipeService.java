package nl.androidappfactory.recipe.services;

import java.util.List;

import nl.androidappfactory.recipe.models.Recipe;

public interface RecipeService {

	public List<Recipe> getAllRecipes();

	public Recipe findById(long l);

}
