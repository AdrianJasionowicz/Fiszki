package com.Fiszki.demo.LoginUser;

import com.Fiszki.demo.LoginUser.Model.RegisterModel;
import com.Fiszki.demo.QuizUser.QuizUser;
import com.Fiszki.demo.QuizUser.QuizUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserService {

    private LoginUserRepository loginUserRepository;
    private PasswordEncoder passwordEncoder;
    private QuizUserRepository quizUserRepository;

    public RegisterUserService(LoginUserRepository loginUserRepository, PasswordEncoder passwordEncoder, QuizUserRepository quizUserRepository) {
        this.loginUserRepository = loginUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.quizUserRepository = quizUserRepository;
    }

    public void registerUser(RegisterModel registerModel) {
        if (loginUserRepository.findByUsername(registerModel.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setRole(Role.ROLE_USER);
        loginUser.setUsername(registerModel.getUsername());
        loginUser.setPassword(passwordEncoder.encode(registerModel.getPassword()));
        QuizUser quizUser = new QuizUser();
        quizUser.setLoginUser(loginUser);
        quizUser.setEmail(registerModel.getEmail());
        quizUser.setName(registerModel.getFirstName());
        loginUser.setQuizUser(quizUser);

        loginUserRepository.save(loginUser);
       // quizUserRepository.save(quizUser);

    }

}
