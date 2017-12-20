package nl.androidappfactory.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import nl.androidappfactory.recipe.converters.IngredientCommandToIngredient;
import nl.androidappfactory.recipe.converters.IngredientToIngredientCommand;
import nl.androidappfactory.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import nl.androidappfactory.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import nl.androidappfactory.recipe.models.Ingredient;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.IngredientRepository;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

public class IngredientServiceImpleTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

	@Mock
	RecipeRepository recipeRepository;

	@Mock
	IngredientRepository ingredientRepository;

	IngredientService ingredientService;

	// init converters
	public IngredientServiceImpleTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
				new UnitOfMeasureCommandToUnitOfMeasure());
		this.unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ingredientService = new IngredientServiceImpl(recipeRepository, ingredientRepository,
				ingredientToIngredientCommand,
				ingredientCommandToIngredient, unitOfMeasureCommandToUnitOfMeasure);
	}

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
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

		// when
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}

	@Test
	public void testSaveRecipeCommand() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		Optional<Recipe> recipeOptional = Optional.of(new Recipe());

		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);

		// when
		IngredientCommand savedCommand = ingredientService.updateIngredient(command);

		// then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));

	}

	@Test
	public void testDeleteById() throws Exception {
		// given
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId(3L);
		recipe.addIngredient(ingredient);
		ingredient.setRecipe(recipe);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		// when
		ingredientService.deleteIngredient(1L, 3L);

		// then
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}

}