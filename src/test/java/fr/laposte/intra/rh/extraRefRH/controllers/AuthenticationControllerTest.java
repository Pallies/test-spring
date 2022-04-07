package fr.laposte.intra.rh.extraRefRH.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.laposte.intra.rh.extraRefRH.config.jwt.JwtAuthenticationEntryPoint;
import fr.laposte.intra.rh.extraRefRH.services.AuthentificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthenticationController.class)
// @WebMvcTest inclut la configuration Spring Security
// WebSecurityConfig requiert un bean de type AuthenticationEntryPoint.
// @Import() complète la configuration du test en ajoutant les beans précisés
@Import(JwtAuthenticationEntryPoint.class)
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthentificationService authentificationService;

    @Autowired
    ObjectMapper mapper;

    @Test
    @WithMockUser(username = "test",password = "tester")
    void userLogin() throws Exception {

        // vérification que le test prend bien en compte la configuration Spring Security
        // l'utilisateur est automatiquement valorisé
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Définition du comportement du service
        // ?? Curieux qu'il ait en paramètre UserDetails ??
        Mockito.when(authentificationService.login(user)).thenReturn(user);

        Map<String, String> body = Map.of("username", "test", "password", "pass");

        // Requête /POST
        mockMvc.perform(
                post("/authentication/login")
                       .header("Content-type", "application/json")
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

}
