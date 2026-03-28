package com.Fiszki.demo.LoginUser;

import com.Fiszki.demo.LoginUser.Model.SettingsModel;
import com.Fiszki.demo.QuizUser.QuizUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private PasswordEncoder passwordEncoder;
    private LoginUserRepository loginUserRepository;

    public SettingsService(PasswordEncoder passwordEncoder, LoginUserRepository loginUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.loginUserRepository = loginUserRepository;
    }


    public ResponseEntity<?> changeUserDetails(SettingsModel settingsModel, Authentication authentication) {
        String username = authentication.getName();
        LoginUser loginUser = loginUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        QuizUser quizUser = loginUser.getQuizUser();

        if (passwordEncoder.matches(settingsModel.getOldPassword(), loginUser.getPassword())) {
            if (settingsModel.getNewPassword() != null && !settingsModel.getNewPassword().isBlank()) {
                loginUser.setPassword(passwordEncoder.encode(settingsModel.getNewPassword()));
            }
            if (settingsModel.getFirstName() != null) {
                quizUser.setName(settingsModel.getFirstName());
            }
            if (settingsModel.getEmail() != null) {
                quizUser.setEmail(settingsModel.getEmail());
            }
            if (settingsModel.getUsername() != null) {
                if (loginUserRepository.findByUsername(settingsModel.getUsername()).isEmpty())
                    loginUser.setUsername(settingsModel.getUsername());
            }
            loginUserRepository.save(loginUser);
            //  quizUserRepository.save(quizUser);
        }else {
            return ResponseEntity.badRequest().body("Niepoprawne hasło");
        }
        return ResponseEntity.ok().build();
    }
}
