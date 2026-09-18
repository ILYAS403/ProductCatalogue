package com.example.product_catalog.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_catalog.Dto.LoginRequest;
import com.example.product_catalog.Security.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

//Pour eviter les problèmes de CORS lors du développement avec Angular, on autorise les requêtes provenant de localhost:4200
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    
@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody LoginRequest request) {

    String username = request.getUsername();
    String password = request.getPassword();

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    username,
                    password
            )
    );

    // ensuite on générera le JWT ici
    String token = jwtService.generateToken(authentication);

    return ResponseEntity.ok(token);
}

}
