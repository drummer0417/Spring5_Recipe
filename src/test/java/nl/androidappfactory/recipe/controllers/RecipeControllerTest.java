package nl.androidappfactory.recipe.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.services.RecipeService;

/**
 * 
 * @author Hans van Meurs
 *
 */

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	Model uiModel;
	RecipeController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new RecipeController(recipeService);
	}

	@Test
	public void testMockMvc() throws Exception {

		String expectedView = "recipe/show";

		Recipe recipe = new Recipe();
		recipe.setId(1l);

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/recipe/show/1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"))
				.andExpect(view().name(expectedView));
	}

	@Test
	public void getRecipePage() throws Exception {

		// given

		Recipe recipe = new Recipe();
		recipe.setId(1l);
		recipe.setDescription("Frikandel");

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		// when
		String viewName = controller.getRecipeById("1", uiModel);

		// then
		assertEquals("recipe/show", viewName);
		verify(recipeService, times(1)).findById(anyLong());
		verify(uiModel, times(1)).addAttribute(eq("recipe"), argumentCaptor.capture());
		Recipe recipeInController = argumentCaptor.getValue();
		assertEquals(recipe.getDescription(), recipeInController.getDescription());
	}
}
