package nl.androidappfactory.recipe.services;

import java.util.List;

import org.springframework.stereotype.Service;

import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public List<Recipe> getAllRecipes() {

		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAll();
		System.out.println("getAll: " + recipes);
		return recipes;
	}

}
