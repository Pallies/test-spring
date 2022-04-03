package fr.laposte.intra.rh.extraRefRH.controllers;


import fr.laposte.intra.rh.extraRefRH.services.AuthentificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<UserDetails> userLogin(@RequestBody UserDetails user) throws UsernameNotFoundException {
        return ResponseEntity.ok().body(authentificationService.login(user));
    }

}
