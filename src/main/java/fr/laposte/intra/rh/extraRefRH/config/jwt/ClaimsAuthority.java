package fr.laposte.intra.rh.extraRefRH.config.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class ClaimsAuthority {

    private final static String ROLES = "roles";
    private final static String APPLICATIONS = "application";

    Map<String, Object> claimsAuth = new HashMap<>();


    public ClaimsAuthority addRoles(Collection<? extends GrantedAuthority> roles) {
        claimsAuth.put(ROLES, roles);
        return this;
    }

//    public ClaimsAuthority addApplication(Set<ApplicationDto> applications) {
//        claimsAuth.put(APPLICATIONS, applications);
//        return this;
//    }

    public Map<String, Object> claimsAuthority() {
        return claimsAuth;
    }
    //downcasting

    public static Collection<? extends GrantedAuthority> getClaimsRoles(Claims claims) {
        Object roles = claims.get(ROLES);
        List<?> rolesList = new ArrayList<>();
        Collection<? extends GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (roles instanceof Set)
            rolesList = new ArrayList<>((Set<?>) roles);
//        vérifie d'abord que la liste est vide et vérifie l'instance
        if (!rolesList.isEmpty() && rolesList.get(0) instanceof GrantedAuthority)
            grantedAuthorities = rolesList.stream()
                    .map(GrantedAuthority.class::cast)
                    .collect(Collectors.toSet());
        return grantedAuthorities;
    }

    //downcasting
//    public static Set<ApplicationDto> getClaimsApplication(Claims claims) {
//        Object applications = claims.get(APPLICATIONS);
//        List<?> applicationList = new ArrayList<>();
//        Set<ApplicationDto> applicationsDto = new HashSet<>();
//
//        if (applications instanceof Set<?>)
//            applicationList = new ArrayList<>((Set<?>) applications);
//        //        vérifie d'abord que la liste est vide et vérifie l'instance
//        if (!applicationList.isEmpty() && applicationList.get(0) instanceof ApplicationDto)
//            applicationsDto = applicationList.stream()
//                    .map(ApplicationDto.class::cast)
//                    .collect(Collectors.toSet());
//        return applicationsDto;
//    }
}
