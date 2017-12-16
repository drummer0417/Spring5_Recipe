package nl.androidappfactory.recipe.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import nl.androidappfactory.recipe.commands.IngredientCommand;
import nl.androidappfactory.recipe.commands.RecipeCommand;
import nl.androidappfactory.recipe.services.IngredientService;
import nl.androidappfactory.recipe.services.RecipeService;

@Controller
public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	IngredientService ingredientService;

	IngredientController controller;

	MockMvc mockMvc;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		controller = new IngredientController(recipeService, ingredientService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void testListIngredients() throws Exception {

		// Given
		RecipeCommand recipeCommand = new RecipeCommand();

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));

		verify(recipeService, times(1)).findCommandById(anyLong());

	}

	@Test
	public void testShowIngredient() throws Exception {

		// Given
		IngredientCommand ingredientCommand = new IngredientCommand();

		when(ingredientService.findByRecipeAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show"))
				.andExpect(model().attributeExists("ingredient"));
	}

	@Test
	public void testDeleteIngredient() throws Exception {

		mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredients"));
	}
}
