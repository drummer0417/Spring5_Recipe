package nl.androidappfactory.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.recipe.services.ImageService;
import nl.androidappfactory.recipe.services.RecipeService;

@Slf4j
@Controller
public class ImageController {

	private ImageService imageService;
	private RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}

	@GetMapping("/recipe/{id}/image")
	public String showImageUploadForm(@PathVariable String id, Model model) {

		log.debug("in showImageUploadForm..");
		model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));

		return "recipe/imageuploadform";
	}

	@PostMapping("/recipe/{id}/image")
	public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {

		log.debug("in handleImagePost, file: " + file.getName());
		imageService.saveImageFile(Long.valueOf(id), file);

		return "redirect:/recipe/" + id + "/show";
	}
}
