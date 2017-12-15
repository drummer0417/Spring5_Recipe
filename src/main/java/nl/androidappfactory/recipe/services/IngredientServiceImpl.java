package nl.androidappfactory.recipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.androidappfactory.recipe.commands.IngredientCommand;
import nl.androidappfactory.recipe.converters.IngredientToIngredientCommand;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

	private RecipeRepository recipeRepository;
	private IngredientToIngredientCommand converter;

	@Autowired
	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand converter) {
		this.recipeRepository = recipeRepository;
		this.converter = converter;
	}

	@Override
	public IngredientCommand findByRecipeAndIngredientId(Long recipeId, Long ingredientId) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe not found");
		}

		// Set<Ingredient> ingredients = recipeOptional.get().getIngredients();
		// for (Ingredient ingredient : ingredients) {
		// if (ingredient.getId().equals(ingredientId)) {
		// return converter.convert(ingredient);
		// }
		// }

		Optional<IngredientCommand> ingredientCommandOptional = recipeOptional.get().getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> converter.convert(ingredient)).findFirst();

		if (ingredientCommandOptional.isPresent()) {
			return ingredientCommandOptional.get();
		} else {
			throw new RuntimeException("Ingredient not found");
		}
	}

}
