package com.example.product_catalog.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final String secretKey =
        "my_super_long_secret_key_123456789";

    private final long expiration = 3600000; // 1 hour
    
 //pour cette methode j ai utiliser dans authController pour générer le token JWT après l'authentification réussie de l'utilisateur   
public String generateToken(Authentication authentication) {

    return Jwts.builder()
            .subject(authentication.getName())
            .claim("role", "USER")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey())
            .compact();
}
// pour les autres methodes j ai utilisé dans JwtAuthenticationFilter pour valider le token JWT et extraire le nom d'utilisateur du token
    public boolean validateToken(String token, UserDetails user) {

        try {
            String username = extractUsername(token);

            return username.equals(user.getUsername())
                    && !isTokenExpired(token);

        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {

        Date expirationDate = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expirationDate.before(new Date());
    }

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }
}