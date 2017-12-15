package nl.androidappfactory.recipe.services;

import nl.androidappfactory.recipe.commands.IngredientCommand;

public interface IngredientService {

	public IngredientCommand findByRecipeAndIngredientId(Long recipeId, Long ingredientId);
}
