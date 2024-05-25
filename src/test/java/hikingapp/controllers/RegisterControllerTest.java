package hikingapp.controllers;

import hikingapp.HikingApp;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ContextConfiguration(classes = HikingApp.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class RegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getRegisterForm() {
        try {
            mvc.perform(get("/register"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("registerForm"));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaaa",        authorities = { "ADMIN" })
    void registerMember() {
        try {
            mvc.perform(post("/register"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("registerForm"));
        }
        catch (Exception e){
            fail();
        }
    }
}