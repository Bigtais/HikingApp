package hikingapp.data.dao;

import hikingapp.data.model.Category;
import hikingapp.data.model.ClubMember;
import hikingapp.data.model.Hike;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface Dao {


    /**
     * Returns the list of all categories.
     * @return The list of all categories.
     */
    List<Category> getAllCategories();

    /**
     * Returns the category with id parameter with associated hikes loaded.
     * @param id The id of the category.
     * @return The category with its associated hikes.
     */
    Category getCategoryWithHikes(Long id);

    /**
     * Adds a category to the repository.
     * @param category The category to add.
     * @return The added category.
     */
    Category addCategory(Category category);


    /**
     * Returns the hike defined by the id with all its details loaded.
     * @param id The id of the hike.
     * @return The hike with all its details.
     */
    Hike getHikeById(Long id);

    /**
     * Searches for a list of hikes by their name attribute corresponding to the chosen pattern.
     * @param pattern The pattern of the name.
     * @return The list of hikes found.
     */
    List<Hike> getHikesByNameLike(String pattern);

    /**
     * Searches for a list of hikes by their description attribute corresponding to the chosen pattern.
     * @param pattern The pattern of the description.
     * @return The list of hikes found.
     */
    List<Hike> getHikesByDescriptionLike(String pattern);

    /**
     * Searches for a list of hikes by their website attribute corresponding to the chosen pattern.
     * @param pattern The pattern of the website string.
     * @return The list of hikes found.
     */
    List<Hike> getHikesByWebsiteLike(String pattern);

    /**
     * Searches for a list of hikes by their date.
     * @param date The date of the hike.
     * @return The list of hikes found.
     */
    List<Hike> getHikesByDate(Date date);

    /**
     * Searches for a list of hikes by a chosen category.
     * @param category The category of the hikes.
     * @return The list of hikes found.
     */
    List<Hike> getHikesByCategory(Category category);

    /**
     * Searches for a list of hikes created by the chosen club member.
     * @param creator The creator of the hike.
     * @return The list of hikes found.
     */
    List<Hike> getHikesByCreator(ClubMember creator);

    /**
     * Returns the list of all club members.
     * @return The list of all club members.
     */
    List<ClubMember> getAllClubMembers();

    /**
     * Searches for a club member with the corresponding email identifier.
     * @param email The email identifying the club member.
     * @return The club member found.
     */
    ClubMember getClubMember(String email);

    /**
     * Searches for a club member with the corresponding id, while also loading its hiking details.
     * @param id The id of the club member.
     * @return The club member found.
     */
    ClubMember getClubMemberWithHikesDetails(Long id);

    /**
     * Adds a hike to the repository.
     * @param hike The hike to add.
     * @return The added hike.
     */
    Hike addHike(Hike hike);

    /**
     * Updates a hike in the repository.
     * @param hike The hike to update.
     * @return The updated hike.
     */
    Hike updateHike(Hike hike);

    /**
     * Deletes a hike from the repository.
     * @param id The id of the hike.
     */
    void deleteHike(Long id);

    /**
     * Adds a club member to the repository.
     * @param clubMember The club member to add.
     * @return The added club member.
     */
    ClubMember addClubMember(ClubMember clubMember);

    /**
     * Updates a club member in the repository.
     * @param clubMember The club member to update.
     * @return The updated club member.
     */
    ClubMember updateClubMember(ClubMember clubMember);
}
