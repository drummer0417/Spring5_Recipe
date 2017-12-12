package nl.androidappfactory.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

	private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {

		this.recipeService = recipeService;
	}

	@RequestMapping("/recipe/show/{id}")
	public String getRecipeById(@PathVariable String id, Model model) {

		Recipe recipe = recipeService.findById(new Long(id));
		log.debug("recipe: " + recipe);
		// log.debug("cat: " + recipe.getCategories());
		// log.debug("directions: " + recipe.getDirections());
		// log.debug("notes: " + recipe.getNotes());
		model.addAttribute("recipe", recipe);

		return "recipe/show";
	}
}
