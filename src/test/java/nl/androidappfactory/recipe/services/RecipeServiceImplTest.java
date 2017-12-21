package nl.androidappfactory.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nl.androidappfactory.recipe.converters.RecipeCommandToRecipe;
import nl.androidappfactory.recipe.converters.RecipeToRecipeCommand;
import nl.androidappfactory.recipe.exceptions.NotFoundException;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	private RecipeService recipeService;

	@Mock
	private RecipeRepository recipeRepository;
	private RecipeCommandToRecipe recipeCommandToRecipe;
	private RecipeToRecipeCommand recipeToRecipeCommand;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);

	}

	@Test
	public void testRecipeById() {

		Recipe recipe = new Recipe();
		recipe.setId(1l);

		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		Recipe recipeReturned = recipeService.findById(1l);

		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();
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
		verify(recipeRepository, times(1)).findAllByOrderByDescription();
	}

	@Test
	public void testDeleteRecipeByID() {

		Long idToDelete = 1l;

		recipeService.deleteByID(idToDelete);

		verify(recipeRepository, times(1)).deleteById(anyLong());
	}

	@Test(expected = NotFoundException.class)
	public void testRecipeByIdNotFoundException() {

		Optional<Recipe> recipeOptional = Optional.empty();

		when(recipeRepository.findById(anyLong())).thenThrow(NotFoundException.class);

		Optional<Recipe> recipeReturned = recipeRepository.findById(1l);
	}
}
