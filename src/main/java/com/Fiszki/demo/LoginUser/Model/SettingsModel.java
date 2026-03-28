package com.Fiszki.demo.LoginUser.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingsModel {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String firstName;
    private String email;
}
