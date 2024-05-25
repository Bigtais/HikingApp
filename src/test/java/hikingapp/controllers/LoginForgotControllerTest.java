package hikingapp.controllers;

import hikingapp.HikingApp;
import hikingapp.data.model.ClubMember;
import hikingapp.services.providers.IClubMemberProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
class LoginForgotControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    IClubMemberProvider clubMemberProvider;

    @Test
    void getPasswordForgotForm() {
        try {
            mvc.perform(get("/password-forgot/ask-mail"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("passwordForgotForm"));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void recoverPassword() {
        try {
            mvc.perform(post("/password-forgot/ask-mail").with(csrf()).param("email", ""))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("passwordForgotForm"));

            var member = new ClubMember();
            member.setFirstName("aaa");
            member.setLastName("aaa");
            member.setPassword("aaaaaaaa");
            member.setEmail("aaa@gmail.com");
            clubMemberProvider.registerUser(member);

            mvc.perform(post("/password-forgot/ask-mail").with(csrf()).param("email", member.getEmail()))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:ask-code"));

        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getReceivedCodeForm() {
        try {
            mvc.perform(get("/password-forgot/ask-code"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());

            var member = new ClubMember();
            member.setFirstName("aaa");
            member.setLastName("aaa");
            member.setPassword("aaaaaaaa");
            member.setEmail("aaa@gmail.com");
            clubMemberProvider.registerUser(member);

            var result = mvc.perform(post("/password-forgot/ask-mail").with(csrf()).param("email", member.getEmail()))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:ask-code")).andReturn();

            var session = (MockHttpSession) result.getRequest().getSession();

            mvc.perform(get("/password-forgot/ask-code").session(session))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("receivedCodeForm"));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void checkCorrectCode() {
        try {
            var member = new ClubMember();
            member.setFirstName("aaa");
            member.setLastName("aaa");
            member.setPassword("aaaaaaaa");
            member.setEmail("aaa@gmail.com");
            clubMemberProvider.registerUser(member);

            var result = mvc.perform(post("/password-forgot/ask-mail").with(csrf()).param("email", member.getEmail()))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:ask-code"))
                    .andReturn();

            var session = (MockHttpSession) result.getRequest().getSession();

            result = mvc.perform(get("/password-forgot/ask-code").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("receivedCodeForm")).andReturn();
            session = (MockHttpSession) result.getRequest().getSession();

            mvc.perform(post("/password-forgot/ask-code").session(session).with(csrf()).param("code", "test"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}