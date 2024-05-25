package hikingapp.controllers;

import hikingapp.HikingApp;
import hikingapp.data.dao.Dao;
import hikingapp.data.model.Category;
import hikingapp.data.model.ClubMember;
import hikingapp.data.model.Hike;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ContextConfiguration(classes = HikingApp.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class HikeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    Dao dao;

    @Test
    @Transactional
    void findHikes() {
        try {
            mvc.perform(post("/hike/find").param("name", "Hike"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("hikesList"))
                    .andExpect(model().attributeExists("hikes"));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getHikeFormAnonymous() {
        try {
            mvc.perform(get("/hike/edit"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaaa")
    void getHikeFormLoggedIn() {
        try {
            mvc.perform(get("/hike/edit"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("hikeForm"));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void createHikeAnonymous() {
        try {
            mvc.perform(post("/hike/edit"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaa@gmail.com")
    void createHikeLoggedIn() {
        var member = new ClubMember();
        member.setFirstName("aaa");
        member.setLastName("aaa");
        member.setPassword("aaaaaaaa");
        member.setEmail("aaa@gmail.com");
        member = dao.addClubMember(member);

        var category = new Category();
        category.setName("test");
        category = dao.addCategory(category);

        var hike = new Hike();
        hike.setName("test");
        hike.setDescription("test");
        hike.setDate(new Date(System.currentTimeMillis() + 100_000));
        hike.setWebsite("https://www.test.com");
        hike.setCategory(category);

        try {
            mvc.perform(post("/hike/edit").flashAttr("hike", hike))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void getHikeDetails() {
        var member = new ClubMember();
        member.setFirstName("aaa");
        member.setLastName("aaa");
        member.setPassword("aaaaaaaa");
        member.setEmail("aaa@gmail.com");
        member = dao.addClubMember(member);

        var category = new Category();
        category.setName("test");
        category = dao.addCategory(category);

        var hike = new Hike();
        hike.setName("aaaaaa");
        hike.setDescription("aaaaaaaaaaaaa");
        hike.setDate(new Date(System.currentTimeMillis() + 100_000));
        hike.setWebsite("https://www.test.com");
        hike.setCategory(category);
        hike = dao.addHike(hike);
        try {
            mvc.perform(get("/hike/" + hike.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("hike"))
                    .andExpect(model().attributeExists("hike"));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}