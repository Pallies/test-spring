package fr.laposte.intra.rh.extraRefRH.controllers;

import fr.laposte.intra.rh.extraRefRH.services.AuthentificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test",password = "tester")
    void userLogin() throws Exception {

        mockMvc.perform(post("/authentication/login")).andExpect(status().isCreated());
    }

    @Test
    void userRegistration() {
    }
}