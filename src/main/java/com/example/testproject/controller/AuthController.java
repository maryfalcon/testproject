package com.example.testproject.controller;

import com.example.testproject.config.security.JWTUtil;
import com.example.testproject.dto.UserCreds;
import com.example.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody UserCreds userCreds) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCreds.getUsername(), userCreds.getPassword()));
        final UserDetails user = userService.loadUserByUsername(userCreds.getUsername());
        final String token = passwordEncoder.matches(userCreds.getPassword(), user.getPassword()) ? jwtUtil.generateToken(user) : null;
        return ResponseEntity.ok(token);
    }

    @RequestMapping(name = "/ping",  method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }
}
