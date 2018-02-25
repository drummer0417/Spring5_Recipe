package nl.androidappfactory.recipe.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.commands.CategoryCommand;
import nl.androidappfactory.recipe.commands.RecipeCommand;
import nl.androidappfactory.recipe.converters.RecipeCommandToRecipe;
import nl.androidappfactory.recipe.converters.RecipeToRecipeCommand;
import nl.androidappfactory.recipe.exceptions.NotFoundException;
import nl.androidappfactory.recipe.models.Recipe;
import nl.androidappfactory.recipe.repositories.RecipeRepository;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;
	private RecipeCommandToRecipe commandToRecipeConverter;
	private RecipeToRecipeCommand recipeToCommandConverter;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe commandToRecipeConverter,
							 RecipeToRecipeCommand recipeToCommandConverter) {
		this.recipeRepository = recipeRepository;
		this.commandToRecipeConverter = commandToRecipeConverter;
		this.recipeToCommandConverter = recipeToCommandConverter;
	}

	@Override
	public List<Recipe> getAllRecipes() {

		log.debug("in: getAllRecipes()");
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAllByOrderByDescription();


		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("/path")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String S = "{\n" +
				"  \"name\": \"hans\",\n" +
				"  \"address\": \"fransebaan 511\",\n" +
				"  \"age\": \"56\"\n" +
				"}";

		//language=RegExp
		String regExp = "^[a-zA-Z0-9_]*$";
		//language=HTML
		String html = "<httm>\n" +
				"\n" +
				"    <head>\n" +
				"        <title>test</title>\n" +
				"    </head>\n" +
				"    <body>\n" +
				"    <h1>This is the body</h1>\n" +
				"    <div>\n" +
				"        \n" +
				"        <p>tekst 1</p>\n" +
				"    </div>\n" +
				"    </body>\n" +
				"</httm>";
		return recipes;
	}


	@Override
	public Recipe findById(long id) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(id);

		if (!recipeOptional.isPresent()) {
			throw new NotFoundException("Recipe not found for id: " + id);
		}
		return recipeOptional.get();
	}

	@Override
	public RecipeCommand findCommandById(Long id) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(id);

		if (!recipeOptional.isPresent()) {
			throw new NotFoundException("Recipe not found for id: " + id);
		}
		return recipeToCommandConverter.convert(recipeOptional.get());

	}

	@Override
	public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

		recipeCommand.getCategories().clear();

		if (recipeCommand.getSelectedCategories() != null) {

			for (String id : recipeCommand.getSelectedCategories()) {
				CategoryCommand categoryCommand = new CategoryCommand();
				categoryCommand.setId(new Long(id));
				recipeCommand.addCategory(categoryCommand);
				log.debug("newCategory, id: " + id);
			}
		}

		Recipe recipe = commandToRecipeConverter.convert(recipeCommand);

		log.debug("#categories after convert: " + recipe.getCategories().size());

		Recipe savedRecipe = null;

		if (recipe != null) {
			savedRecipe = recipeRepository.save(recipe);
		}

		return recipeToCommandConverter.convert(savedRecipe);
	}

	@Override
	public void deleteByID(Long id) {

		recipeRepository.deleteById(id);

	}

}
