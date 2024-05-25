package hikingapp.data.dao.repositories;

import hikingapp.data.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    @EntityGraph(value = "categories-with-hikes")
    @Query(value = "SELECT cat FROM Category cat WHERE cat.id = :id")
    Category findCategoryByIdWithHikes(Long id);
}
