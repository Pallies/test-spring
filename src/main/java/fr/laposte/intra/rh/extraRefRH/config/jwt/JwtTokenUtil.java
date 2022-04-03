package fr.laposte.intra.rh.extraRefRH.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;


public final class JwtTokenUtil {

    private static Long JWT_TOKEN_VALIDITY=18000L;

    private static String SECRET_KEY="secret-key-exemple";

    // recherche de l'utilisateur dans le token
    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //    recherche de la date de création
    public static Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    //    recherche de la date de la date d'expiration
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //    recherche des roles
    public static Collection<? extends GrantedAuthority> getAuthoritiesFromToken(String token) {
        return getClaimFromToken(token, ClaimsAuthority::getClaimsRoles);
    }

//    //    recherche des applications autorisées
//    public static Set<ApplicationDto> getApplicationsFromToken(String token) {
//        return getClaimFromToken(token, ClaimsAuthority::getClaimsApplication);
//    }

    // vérification de la validité du jeton et de la validité de l'utilisateur
    public static Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    //    vérification de la date d'expiration (vrai si la date est expirée)
    private static Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    //  extraction de la données spécifique du token
    private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claimsTFunction.apply(claims);
    }

    //génération du token
    public static String generateToken(UserDetails userDetails) {
//        add user identity
        ClaimsAuthority jwtAuthority = new ClaimsAuthority()
                .addRoles(userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(jwtAuthority.claimsAuthority())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String generateAuthenticationToken(UserDetails userDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtTokenUtil.generateToken(userDetails);
    }

    public static void authenticationByToken(HttpServletRequest request, String identifier, Collection<? extends GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                identifier, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
