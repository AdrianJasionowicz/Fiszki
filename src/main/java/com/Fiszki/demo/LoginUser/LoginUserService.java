package com.Fiszki.demo.LoginUser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements UserDetailsService {

    private final LoginUserRepository loginUserRepository;
    private LoginUserRepository repository;

    public LoginUserService(LoginUserRepository repository, LoginUserRepository loginUserRepository) {
        this.repository = repository;
        this.loginUserRepository = loginUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }



}
