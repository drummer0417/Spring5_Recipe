package nl.androidappfactory.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.commands.IngredientCommand;
import nl.androidappfactory.recipe.commands.RecipeCommand;
import nl.androidappfactory.recipe.services.IngredientService;
import nl.androidappfactory.recipe.services.RecipeService;

@Slf4j
@Controller
public class IngredientController {

	RecipeService recipeService;
	IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String getAllIngredients(@PathVariable String recipeId, Model model) {

		log.debug("Getting ingredients: ");
		RecipeCommand recipeCommand = recipeService.findCommandById(new Long(recipeId));
		model.addAttribute("recipe", recipeCommand);

		return "recipe/ingredient/list";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String getIngredient(
			@PathVariable Long recipeId,
			@PathVariable Long ingredientId,
			Model model) {

		IngredientCommand ingredientCommand = ingredientService.findByRecipeAndIngredientId(recipeId, ingredientId);
		log.debug("after call service: " + ingredientCommand);
		model.addAttribute("ingredient", ingredientCommand);

		return "recipe/ingredient/show";
	}
}
