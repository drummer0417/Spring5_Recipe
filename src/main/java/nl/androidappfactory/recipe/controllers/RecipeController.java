package nl.androidappfactory.recipe.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.commands.RecipeCommand;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.services.CategoryService;
import nl.androidappfactory.recipe.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {

	private RecipeService recipeService;
	private CategoryService categoryService;

	public RecipeController(RecipeService recipeService, CategoryService categoryService) {
		this.recipeService = recipeService;
		this.categoryService = categoryService;
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

		RecipeCommand recipeCommand = recipeService.findCommandById(new Long(id));

		List<String> currentCategoryIds = new ArrayList<>();
		recipeCommand.getCategories().forEach(category -> currentCategoryIds.add(String.valueOf(category.getId())));

		model.addAttribute("recipe", recipeCommand);
		model.addAttribute("categoryList", categoryService.getAllCategories());
		model.addAttribute("currentCategoryIds", currentCategoryIds);

		return "recipe/recipeform";
	}

	@PostMapping("/recipe")
	public String saveORUpdate(@ModelAttribute RecipeCommand recipeCommand) {

		log.debug("in saveOrUpdate: " + Arrays.toString(recipeCommand.getSelectedCategories()));

		RecipeCommand recipeAfterSave = recipeService.saveRecipeCommand(recipeCommand);
		log.debug("after save: " + recipeAfterSave.getId());
		return "redirect:/recipe/" + recipeAfterSave.getId() + "/show";
	}

	@GetMapping("recipe/{id}/delete")
	public String delete(@PathVariable String id) {
		log.debug("in delete, id: " + id);
		recipeService.deleteByID(new Long(id));
		return "redirect:/index";
	}
}
