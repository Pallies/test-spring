package fr.laposte.intra.rh.extraRefRH.controllers;


import fr.laposte.intra.rh.extraRefRH.models.LoginUser;
import fr.laposte.intra.rh.extraRefRH.services.AuthentificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Login controller.
 */
@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    private AuthentificationService authentificationService;

    // J'ai crée une structure LoginUser en entrée du contrôleur.
    // Il n'est pas logique que pour s'authentifier on ait à fournir l'intégralité du contexte
    @PostMapping("/login")
    public ResponseEntity<UserDetails> userLogin(@RequestBody LoginUser user, @AuthenticationPrincipal UserDetails userDetails) throws UsernameNotFoundException {

        // Validation de l'injection de l'utilisateur connecté via @AuthenticationPrincipal
        return ResponseEntity.ok().body(authentificationService.login(userDetails));
    }

}
