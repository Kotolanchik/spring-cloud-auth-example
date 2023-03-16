package ru.kolodkin.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kolodkin.auth.bean.AccountCreateRequest;
import ru.kolodkin.auth.bean.JwtResponse;
import ru.kolodkin.auth.service.AccountService;
import ru.kolodkin.auth.util.JwtTokenUtility;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtility tokenUtility;
    private final AccountService accountService;

    @PostMapping("/authentication")
    public ResponseEntity<JwtResponse> authenticationAccount(@RequestBody AccountCreateRequest request) {
        try {
            authenticateAccount(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new JwtResponse(tokenUtility.generateToken(accountService
                    .loadUserByUsername(request.getUsername()))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> registerAccount(@RequestBody AccountCreateRequest request) {
        if (request == null) {
            throw new RuntimeException();
        }
        accountService.save(request);
        return ResponseEntity.ok().build();
    }

    private void authenticateAccount(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
