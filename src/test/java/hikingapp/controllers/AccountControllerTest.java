package hikingapp.controllers;

import hikingapp.HikingApp;
import hikingapp.data.model.ClubMember;
import hikingapp.services.providers.IClubMemberProvider;
import hikingapp.utils.PasswordRequestUtil;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ContextConfiguration(classes = HikingApp.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    IClubMemberProvider clubMemberProvider;

    @Test
    @WithMockUser(username = "aaa@gmail.com")
    void getAccountDetails() {
        var member = new ClubMember();
        member.setFirstName("aaa");
        member.setLastName("aaa");
        member.setPassword("aaaaaaaa");
        member.setEmail("aaa@gmail.com");
        clubMemberProvider.registerUser(member);
        try {
            mvc.perform(get("/account"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("member"))
                    .andExpect(model().attributeExists("member"));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaa@gmail.com")
    void getPasswordEditForm() {
        try {
            mvc.perform(get("/account/password-edit"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("passwordEditForm"));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    @WithMockUser(username = "aaa@gmail.com", password = "aaaaaaaa")
    void updatePassword() {
        var member = new ClubMember();
        member.setFirstName("aaa");
        member.setLastName("aaa");
        member.setPassword("aaaaaaaa");
        member.setEmail("aaa@gmail.com");
        clubMemberProvider.registerUser(member);
        try {
            mvc.perform(post("/account/password-edit").with(csrf()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("passwordEditForm"));

            var util = new PasswordRequestUtil();
            util.setOldPassword("aaaaaaaa");
            util.setNewPassword("abababab");
            util.setConfirmPassword("abababab");

            mvc.perform(post("/account/password-edit").with(csrf()).flashAttr("passwordRequestUtil", util))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/account"));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}