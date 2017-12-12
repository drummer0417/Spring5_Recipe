package nl.androidappfactory.recipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public List<Recipe> getAllRecipes() {

		log.debug("in: getAllRecipes()");
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();

		return recipes;
	}

	@Override
	public Recipe findById(long id) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(id);

		if (!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe not found");
		}
		return recipeOptional.get();
	}
}
