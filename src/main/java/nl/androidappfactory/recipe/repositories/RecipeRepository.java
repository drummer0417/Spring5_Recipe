package nl.androidappfactory.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import nl.androidappfactory.recipe.models.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
