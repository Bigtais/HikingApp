package hikingapp.controllers;

import hikingapp.HikingApp;
import hikingapp.data.dao.Dao;
import hikingapp.data.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    Dao dao;

    @Test
    void listCategories() {
        try {
            mvc.perform(get("/category/list"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("categoriesList"))
                    .andExpect(model().attributeExists("categories"));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void getCategoryFormAnonymous() {
        try {
            mvc.perform(get("/category/create"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaaa")
    void getCategoryFormNotAdmin() {
        try {
            mvc.perform(get("/category/create"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaaa",        authorities = { "ADMIN" })
    void getCategoryFormAdmin() {
        try {
            mvc.perform(get("/category/create"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("categoryForm"));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void createCategoryAnonymous() {
        try {
            mvc.perform(post("/category/create"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaaa")
    void createCategoryNotAdmin() {
        try {
            mvc.perform(post("/category/create"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaaa",        authorities = { "ADMIN" })
    void createCategoryAdmin() {
        try {
            mvc.perform(post("/category/create"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("categoryForm"));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void getCategoryDetails() {
        var category = new Category();
        category.setName("AAA");
        category = dao.addCategory(category);
        try {
            mvc.perform(get("/category/" + category.getId()))
                    .andExpect(status().isOk())
                    .andExpect(view().name("category"))
                    .andExpect(model().attributeExists("category"));
        }
        catch (Exception e){
            fail();
        }
    }
}