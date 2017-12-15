package nl.androidappfactory.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.commands.RecipeCommand;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

	private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {

		this.recipeService = recipeService;
	}

	@RequestMapping("/recipe/{id}/show")
	public String getRecipeById(@PathVariable String id, Model model) {

		Recipe recipe = recipeService.findById(new Long(id));
		log.debug("recipe: " + recipe);
		model.addAttribute("recipe", recipe);

		return "recipe/show";
	}

	@RequestMapping("/recipe/new")
	public String getNewRecipeForm(Model model) {

		log.debug("in /recipe/new");

		model.addAttribute("recipe", new RecipeCommand());

		return "recipe/recipeform";
	}

	@RequestMapping("/recipe/{id}/update")
	public String getUpdateRecipeForm(@PathVariable String id, Model model) {
		log.debug("updaten maar, id: " + id);
		model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
		return "recipe/recipeform";
	}

	@PostMapping("/recipe")
	public String saveORUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		log.debug("in saveOrUpdate: " + recipeCommand);

		RecipeCommand recipeAfterSave = recipeService.saveRecipeCommand(recipeCommand);
		log.debug("after save: " + recipeAfterSave);
		return "redirect:/recipe/" + recipeAfterSave.getId() + "/show";
	}
}
