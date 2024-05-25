package hikingapp.data.dao.repositories;

import hikingapp.data.model.Category;
import hikingapp.data.model.ClubMember;
import hikingapp.data.model.Hike;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Date;
import java.util.List;

public interface HikeRepository extends JpaRepository<Hike, Long> {

    @EntityGraph(value = "hikes-with-creator-and-category")
    Hike getHikeById(Long id);

    List<Hike> getHikesByNameLikeIgnoreCase(String pattern);

    List<Hike> getHikesByDescriptionLike(String pattern);

    List<Hike> getHikesByWebsiteLike(String pattern);

    List<Hike> getHikesByDate(Date date);

    List<Hike> getHikesByCategory(Category category);

    List<Hike> getHikesByCreator(ClubMember clubMember);

    @Modifying
    void deleteHikeById(Long id);
}
