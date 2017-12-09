package nl.androidappfactory.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import nl.androidappfactory.recipe.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
