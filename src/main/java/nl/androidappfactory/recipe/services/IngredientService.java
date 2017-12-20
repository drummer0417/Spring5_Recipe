package nl.androidappfactory.recipe.services;

import nl.androidappfactory.recipe.commands.IngredientCommand;

public interface IngredientService {

	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

	public void deleteIngredient(long recipeId, long ingredientId);

	public IngredientCommand updateIngredient(IngredientCommand igredientCommand);

	public IngredientCommand createIngredient(IngredientCommand ingredientCommand);
}
