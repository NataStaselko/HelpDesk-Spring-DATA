package com.staselko.HelpDesk.controller;

import com.staselko.HelpDesk.security.JwtRequest;
import com.staselko.HelpDesk.security.JwtResponse;
import com.staselko.HelpDesk.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class JwtController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody @Valid JwtRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        final String userName = jwtUtil.getUsernameFromToken(token);
        final String role = jwtUtil.getRoleByUserFromToken(token);
        return ResponseEntity.ok(new JwtResponse(token, userName, role));
    }

    @PostMapping("exit")
    public ResponseEntity<Void> logout() {
       SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
