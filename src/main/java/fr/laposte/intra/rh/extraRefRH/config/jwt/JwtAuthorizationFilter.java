package fr.laposte.intra.rh.extraRefRH.config.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Configuration
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        String identifier;
        String jwtToken;

        jwtToken = parseJwt(request);// récupération du jeton

        if (jwtToken != null && JwtTokenUtil.validateToken(jwtToken)) {
            //  extraction des informations
            identifier = JwtTokenUtil.getUsernameFromToken(jwtToken);
            Collection<? extends GrantedAuthority> authorities = JwtTokenUtil.getAuthoritiesFromToken(jwtToken);
            JwtTokenUtil.authenticationByToken(request, identifier, authorities);
        }

        chain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}