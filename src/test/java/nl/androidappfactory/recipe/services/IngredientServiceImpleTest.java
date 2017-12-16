package nl.androidappfactory.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nl.androidappfactory.recipe.commands.IngredientCommand;
import nl.androidappfactory.recipe.converters.IngredientToIngredientCommand;
import nl.androidappfactory.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import nl.androidappfactory.recipe.models.Ingredient;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.IngredientRepository;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

public class IngredientServiceImpleTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	IngredientRepository ingredientRepository;

	IngredientService ingredientService;

	// init converters
	public IngredientServiceImpleTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientRepository,
				ingredientToIngredientCommand);
	}

	@Test
	public void findByRecipeIdAndId() throws Exception {}

	@Test
	public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
		// given
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(1L);

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		// then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeAndIngredientId(1L, 3L);

		// when
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}

	@Test
	public void testDeleteRecipe() {

		// Given
		Recipe recipe = new Recipe();
		Ingredient i1 = new Ingredient();
		i1.setId(new Long(1));
		Ingredient i2 = new Ingredient();
		i2.setId(new Long(2));

		recipe.addIngredient(i1);
		recipe.addIngredient(i2);

		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		// when
		ingredientService.deleteIngredient(1l, 2l);

		// then
		verify(recipeRepository, times(1)).save(recipeOptional.get());
	}

}