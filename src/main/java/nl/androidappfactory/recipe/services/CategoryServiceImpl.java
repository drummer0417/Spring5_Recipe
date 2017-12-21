package nl.androidappfactory.recipe.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.models.Category;
import nl.androidappfactory.recipe.repositories.CategoryRepository;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Set<Category> getAllCategories() {

		log.debug("in getAllCategories: ");

		Set<Category> categories = new HashSet<>();

		categoryRepository.findAll().forEach(category -> categories.add(category));

		return categories;
	}

}
