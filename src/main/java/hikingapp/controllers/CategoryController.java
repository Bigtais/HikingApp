package hikingapp.controllers;

import hikingapp.data.model.Category;
import hikingapp.services.providers.ICategoriesProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for displaying categories and their details.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoriesProvider categoriesProviderService;

    /**
     * Show the list of categories in the app.
     * @return The view containing the list of categories.
     */
    @RequestMapping("/list")
    public ModelAndView listCategories() {
        var categories = categoriesProviderService.getAllCategories();
        return new ModelAndView("categoriesList", "categories", categories);
    }

    /**
     * Show the category creation form.
     * @param category The category to view.
     * @return The category creation form.
     */
    @GetMapping("/create")
    public String getCategoryForm(@ModelAttribute Category category) {
        return "categoryForm";
    }

    /**
     * Creates a category with the data from the form.
     * @param category The category to create.
     * @param result The result of the validation of the Category object.
     * @return A redirection to the page of the created category, else the category form with the errors found
     */
    @PostMapping("/create")
    public String createCategory(@ModelAttribute @Valid Category category, BindingResult result) {
        if (result.hasErrors())
            return "categoryForm";
        categoriesProviderService.createCategory(category);
        return "redirect:" + category.getId().toString();
    }

    /**
     * Show the category corresponding to the ID in the path with its details.
     * @param categoryId The ID of the category to display.
     * @return The view containing the details of the category.
     */
    @RequestMapping("/{categoryId}")
    public ModelAndView getCategoryDetails(@PathVariable Long categoryId) {
        var category = categoriesProviderService.getCategoryWithHikes(categoryId);
        if (category == null)
            throw new NotFoundException("Element not found");
        return new ModelAndView("category", "category", category);
    }

}
