package com.Fiszki.demo.LoginUser;

import com.Fiszki.demo.LoginUser.Model.SettingsModel;
import com.Fiszki.demo.QuizUser.QuizUser;
import com.Fiszki.demo.QuizUser.QuizUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements UserDetailsService {

    private final LoginUserRepository loginUserRepository;
    private final QuizUserRepository quizUserRepository;

    public LoginUserService(LoginUserRepository loginUserRepository, QuizUserRepository quizUserRepository) {
        this.loginUserRepository = loginUserRepository;
        this.quizUserRepository = quizUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }




}

