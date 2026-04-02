package com.Fiszki.demo.LoginUser;

import com.Fiszki.demo.LoginUser.Model.SettingsModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PutMapping("/User")
    public ResponseEntity<?> changePassword(@RequestBody SettingsModel settingsModel, Authentication authentication) {


        return settingsService.changeUserDetails(settingsModel, authentication);
    }


}
