package nl.androidappfactory.recipe.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.commands.IngredientCommand;
import nl.androidappfactory.recipe.converters.IngredientToIngredientCommand;
import nl.androidappfactory.recipe.models.Ingredient;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.IngredientRepository;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private RecipeRepository recipeRepository;
	private IngredientRepository ingredientRepository;
	private IngredientToIngredientCommand converter;

	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
			IngredientToIngredientCommand converter) {
		this.recipeRepository = recipeRepository;
		this.ingredientRepository = ingredientRepository;
		this.converter = converter;
	}

	@Override
	public IngredientCommand findByRecipeAndIngredientId(Long recipeId, Long ingredientId) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {

			// Todo: Add real error handling here
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
			// Todo: Add real error handling here
			throw new RuntimeException("Ingredient not found");
		}
	}

	@Override
	public void deleteIngredient(long recipeId, long ingredientId) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			// Todo: Add real error handling here
			throw new RuntimeException("Recipe not found");
		}
		Recipe recipe = recipeOptional.get();

		Ingredient ingredientToRemove = null;

		for (Ingredient ingredient : recipe.getIngredients()) {
			if (ingredient.getId().equals(ingredientId)) {

				ingredientToRemove = ingredient;
				break;
			}
		}

		recipe.removeIngredient(ingredientToRemove);
		recipeRepository.save(recipe);
		ingredientRepository.deleteById(ingredientToRemove.getId());

		log.debug("after save(): " + recipe);
	}

}
