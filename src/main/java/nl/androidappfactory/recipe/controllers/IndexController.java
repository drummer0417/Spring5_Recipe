package nl.androidappfactory.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.androidappfactory.recipe.repositories.CategoryRepository;
import nl.androidappfactory.recipe.repositories.UnitOfMeasureRepository;
import nl.androidappfactory.recipe.services.RecipeService;

@Controller
public class IndexController {

	private RecipeService recipeService;

	public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
			RecipeService recipeService) {

		this.recipeService = recipeService;
	}

	@RequestMapping({ "/", "", "index", "recipes" })
	public String getAllRecipes(Model model) {

		model.addAttribute("recipes", recipeService.getAllRecipes());
		return "index";
	}

}
