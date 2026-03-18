package com.Fiszki.demo.LoginUser.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterModel {
    private String username;
    private String password;
    private String firstName;
    private String email;
}
