package nl.androidappfactory.recipe.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.ui.Model;

import nl.androidappfactory.recipe.services.RecipeService;

/**
 * Created by jt on 6/17/17.
 */

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	RecipeController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new RecipeController(recipeService);
	}

	@Test
	public void testMockMvc() throws Exception {

		String expectedView = "recipe/show";

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/recipe/show/1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"))
				.andExpect(view().name(expectedView));
	}
}
