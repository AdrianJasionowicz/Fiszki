package com.Fiszki.demo.LoginUser;

import jdk.jfr.Registered;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Registered
public interface LoginUserRepository extends CrudRepository<LoginUser, Long> {
    Optional<LoginUser> findByUsername(String username);

}
