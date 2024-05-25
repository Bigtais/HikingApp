package hikingapp.data.dao;

import hikingapp.data.model.Category;
import hikingapp.data.model.ClubMember;
import hikingapp.data.model.Hike;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class JpaDaoTest {

    @Autowired
    private Dao dao;

    @Test
    void testDaoMethodsCategory() {
        var category1 = new Category();
        category1.setName("Name 1");

        var category2 = new Category();
        category2.setName("Name 2");

        ///////////////////////////////////////////////////////////////////////////
        //  Create (add) a category
        ///////////////////////////////////////////////////////////////////////////
        category1 = dao.addCategory(category1);
        category2 = dao.addCategory(category2);

        ///////////////////////////////////////////////////////////////////////////
        //  Retrieve all categories without hikes
        ///////////////////////////////////////////////////////////////////////////
        List<Category> allCategories = dao.getAllCategories();
        assertEquals(2+5, allCategories.size()); // +5 categories created during app init

        for (Category category:allCategories) {
            assertThrows(LazyInitializationException.class, () -> category.getHikes().size());
        }

        ///////////////////////////////////////////////////////////////////////////
        //  Retrieve a category with its hikes
        ///////////////////////////////////////////////////////////////////////////
        var retrievedCategory = dao.getCategoryWithHikes(category1.getId());
        assertEquals(category1.getId(), retrievedCategory.getId());
        assertDoesNotThrow(() -> retrievedCategory.getHikes().size());
    }

    @Test
    void testDaoMethodsHike() {
        var clubMember1 = new ClubMember();
        clubMember1.setFirstName("aaa");
        clubMember1.setLastName("aaa");
        clubMember1.setPassword("aaaaaaaa");
        clubMember1.setEmail("aaa@gmail.com");

        var clubMember2 = new ClubMember();
        clubMember2.setFirstName("bbb");
        clubMember2.setLastName("bbb");
        clubMember2.setPassword("bbbbbbbb");
        clubMember2.setEmail("bbb@gmail.com");

        var category1 = new Category();
        category1.setName("Name 1");

        var category2 = new Category();
        category2.setName("Name 2");

        var hike1 = new Hike();
        var hike2 = new Hike();
        var hike3 = new Hike();

        var calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER,15);
        var date1 = calendar.getTime();

        calendar.set(2024, Calendar.JUNE, 15);
        var date2 = calendar.getTime();

        hike1.setDate(date1);
        hike2.setDate(date2);
        hike3.setDate(date2);

        hike1.setDescription("Il se passe un truc");
        hike2.setDescription("Découverte des calanques depuis la mer.");
        hike3.setDescription("Découverte des calanques depuis le parc national.");

        hike1.setName("Created 1 calanques");
        hike2.setName("Name 1 calanques");
        hike3.setName("Created 2 calanques");

        hike1.setWebsite("https://secure.com");
        hike2.setWebsite("http://secure.com");
        hike3.setWebsite("https://secure.com");

        category1.addHike(hike1);
        category1.addHike(hike2);
        category2.addHike(hike3);

        clubMember1.addHike(hike1);
        clubMember2.addHike(hike2);
        clubMember2.addHike(hike3);

        category1 = dao.addCategory(category1);
        category2 = dao.addCategory(category2);

        clubMember1 = dao.addClubMember(clubMember1);
        clubMember2 = dao.addClubMember(clubMember2);

        ///////////////////////////////////////////////////////////////////////////
        //  Create (add) a hike
        ///////////////////////////////////////////////////////////////////////////
        hike1 = dao.addHike(hike1);
        hike2 = dao.addHike(hike2);
        hike3 = dao.addHike(hike3);

        ///////////////////////////////////////////////////////////////////////////
        //  Retrieve a hike with all details
        ///////////////////////////////////////////////////////////////////////////
        var retrievedHike = dao.getHikeById(hike1.getId());
        assertEquals(hike1.getId(), retrievedHike.getId());
        assertDoesNotThrow(retrievedHike::getCreator);
        assertDoesNotThrow(retrievedHike::getCategory);

        ///////////////////////////////////////////////////////////////////////////
        //  Retrieve hikes from criteria
        ///////////////////////////////////////////////////////////////////////////
        List<Hike> retrievedByCategory = dao.getHikesByCategory(category1);
        assertEquals(2, retrievedByCategory.size());

        List<Hike> retrievedByCreator = dao.getHikesByCreator(clubMember2);
        assertEquals(2, retrievedByCreator.size());

        List<Hike> retrievedByDescription = dao.getHikesByDescriptionLike("%calanques%");
        assertEquals(2, retrievedByDescription.size());

        List<Hike> retrievedByName = dao.getHikesByNameLike("%Created%");
        assertEquals(2, retrievedByName.size());

        List<Hike> retrievedByWebsite = dao.getHikesByWebsiteLike("https%");
        assertEquals(2+25, retrievedByWebsite.size()); // +25 for the hikes created during app init

        List<Hike> retrievedByDate = dao.getHikesByDate(date2);
        assertEquals(2, retrievedByDate.size());

        ///////////////////////////////////////////////////////////////////////////
        //  Modify a hike
        ///////////////////////////////////////////////////////////////////////////
        hike1.setName("Balade aux calanques");
        var updatedHike1 = dao.updateHike(hike1);

        updatedHike1 = dao.getHikeById(hike1.getId());
        assertEquals(hike1.getName(), updatedHike1.getName());

        ///////////////////////////////////////////////////////////////////////////
        //  Delete a hike
        ///////////////////////////////////////////////////////////////////////////
        dao.deleteHike(hike3.getId());

        var nonExistentHike = dao.getHikeById(hike3.getId());
        assertNull(nonExistentHike);
    }

    @Test
    void testDaoMethodsClubMember() {
        var clubMember1 = new ClubMember();
        clubMember1.setFirstName("aaa");
        clubMember1.setLastName("aaa");
        clubMember1.setPassword("aaaaaaaa");
        clubMember1.setEmail("aa@gmail.com");

        var clubMember2 = new ClubMember();
        clubMember2.setFirstName("bbb");
        clubMember2.setLastName("bbb");
        clubMember2.setPassword("bbbbbbbb");
        clubMember2.setEmail("bb@gmail.com");

        ///////////////////////////////////////////////////////////////////////////
        //  Create (add) a club member
        ///////////////////////////////////////////////////////////////////////////
        clubMember1 = dao.addClubMember(clubMember1);
        clubMember2 = dao.addClubMember(clubMember2);

        ///////////////////////////////////////////////////////////////////////////
        //  Retrieve all club members with no details
        ///////////////////////////////////////////////////////////////////////////
        List<ClubMember> allClubMembers = dao.getAllClubMembers();
        assertEquals(2+1+5, allClubMembers.size()); // +1 member for admin + 5 created users during app init

        for (ClubMember clubMember:allClubMembers) {
            assertThrows(LazyInitializationException.class, () -> clubMember.getHikes().size());
        }

        ///////////////////////////////////////////////////////////////////////////
        //  Retrieve one particular club member with no details
        ///////////////////////////////////////////////////////////////////////////
        var retrievedClubMember = dao.getClubMember(clubMember1.getEmail());
        assertEquals(clubMember1.getId(), retrievedClubMember.getId());
        assertThrows(LazyInitializationException.class, () -> retrievedClubMember.getHikes().size());

        ///////////////////////////////////////////////////////////////////////////
        //  Retrieve one particular club member with its hikes
        ///////////////////////////////////////////////////////////////////////////
        var retrievedClubMemberWithHikes = dao.getClubMemberWithHikesDetails(clubMember2.getId());
        assertEquals(clubMember2.getId(), retrievedClubMemberWithHikes.getId());
        assertDoesNotThrow(() -> retrievedClubMemberWithHikes.getHikes().size());

        ///////////////////////////////////////////////////////////////////////////
        //  Modify a club member
        ///////////////////////////////////////////////////////////////////////////
        clubMember1.setFirstName("Rayane");
        var updatedClubMember1 = dao.updateClubMember(clubMember1);

        updatedClubMember1 = dao.getClubMember(clubMember1.getEmail());
        assertEquals(clubMember1.getFirstName(), updatedClubMember1.getFirstName());
    }
}