package hikingapp.services.providers;

import hikingapp.data.model.Category;

import java.util.List;
import java.util.Map;

/**
 * General interface for category search operation.
 */
public interface ICategoriesProvider {

    /**
     * Returns all the existing categories.
     * @return The list of categories found.
     */
    List<Category> getAllCategories();

    /**
     * Returns a specific category with its associated hikes.
     * @param id The id of the category to search for.
     * @return The found category with its associated hikes, or null if not found.
     */
    Category getCategoryWithHikes(Long id);

    /**
     * Returns the all the categories as a map with the keys as the categories
     * and the value as the name of the category.
     * @return The Map of all the categories.
     */
    Map<Category, String> getCategoriesAsMap();

    /**
     * Creates a category.
     * @param category the category to create.
     */
    void createCategory(Category category);
}
