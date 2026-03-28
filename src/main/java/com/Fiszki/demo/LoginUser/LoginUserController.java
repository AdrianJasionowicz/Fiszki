package com.Fiszki.demo.LoginUser;

import com.Fiszki.demo.LoginUser.Config.JwtUtil;
import com.Fiszki.demo.LoginUser.Model.LoginRequest;
import com.Fiszki.demo.LoginUser.Model.RegisterModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginUserController {
    private RegisterUserService registerUserService;
    private LoginUserService loginUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public LoginUserController(RegisterUserService registerUserService, LoginUserService loginUserService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.registerUserService = registerUserService;
        this.loginUserService = loginUserService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterModel registerModel) {
        registerUserService.registerUser(registerModel);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(request.getUsername());

            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .domain("localhost")
                    .path("/")
                    .maxAge(86400)
                    .build();

            response.setHeader("Set-Cookie", cookie.toString());

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błędny login lub hasło");
        }
    }

}
