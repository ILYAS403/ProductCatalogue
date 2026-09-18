package com.example.product_catalog.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService  jwtService;
    // UserDetailsService est utilisé pour charger les détails de l'utilisateur
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;

        this.userDetailsService = userDetailsService;
    }

   @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

    if (request.getRequestURI().equals("/api/auth/login")) {
        filterChain.doFilter(request, response);
        return;
    }
    // On récupère le token JWT depuis l'en-tête Authorization
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }
   // On extrait le token JWT de l'en-tête Authorization car il est précédé de "Bearer " 7 lettres
    String token = authHeader.substring(7);

    // On extrait le nom d'utilisateur du token JWT
    String username = jwtService.extractUsername(token);


    // On charge les détails de l'utilisateur à partir du nom d'utilisateur extrait du token
    UserDetails userDetails =
            userDetailsService.loadUserByUsername(username);

    // On valide le token JWT en vérifiant qu'il correspond à l'utilisateur et qu'il n'est pas expiré
    if (jwtService.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }


  //je fini mon boulot continue les autres filtres de la chaîne de filtres
    filterChain.doFilter(request, response);
}
}
