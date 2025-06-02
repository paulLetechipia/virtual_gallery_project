
package com.proyectoart.controller;

import com.proyectoart.model.User;
import com.proyectoart.model.Role;
import com.proyectoart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyectoart.model.LoginRequest;
import com.proyectoart.security.JwtUtil;
import com.proyectoart.security.MyUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
    String jwt = jwtUtil.generateToken(userDetails.getUsername());
    return ResponseEntity.ok(jwt);
}

    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Auth module is live");
    }
}
