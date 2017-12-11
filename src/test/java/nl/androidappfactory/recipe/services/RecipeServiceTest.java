package nl.androidappfactory.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

public class RecipeServiceTest {

	private RecipeService recipeService;

	@Mock
	private RecipeRepository recipeRepository;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository);

	}

	@Test
	public void testGetAllRecipes() {

		List<Recipe> recipes = new ArrayList<>();
		recipes.add(new Recipe());
		long expectedRecipesReturned = 1;

		when(recipeService.getAllRecipes()).thenReturn(recipes);

		// verify that recipeRepository.getAllRecipes returns a list with 1 recipe
		assertEquals(expectedRecipesReturned, recipeService.getAllRecipes().size());

		// verify that recipeRepository.getAllRecipes is called once and onde only
		verify(recipeRepository, times(1)).findAll();
	}

}
