package hikingapp.services.providers;

import hikingapp.data.dao.Dao;
import hikingapp.data.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service dedicated to the search of categories.
 */
@Service
public class CategoriesProviderService implements ICategoriesProvider{

    @Autowired
    Dao dao;

    public List<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    public Category getCategoryWithHikes(Long id) {
        return dao.getCategoryWithHikes(id);
    }

    public Map<Category, String> getCategoriesAsMap() {
        Map<Category, String> categoriesMap = new LinkedHashMap<>();
        var categories = dao.getAllCategories();
        for (Category category:categories) {
            categoriesMap.put(category, category.getName());
        }
        return categoriesMap;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void createCategory(Category category) {
        category.setHikes(new ArrayList<>());
        dao.addCategory(category);
    }

}
