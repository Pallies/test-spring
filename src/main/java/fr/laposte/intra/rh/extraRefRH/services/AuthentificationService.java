package fr.laposte.intra.rh.extraRefRH.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {

    public UserDetails login(UserDetails user){
        return user;
    }
}
