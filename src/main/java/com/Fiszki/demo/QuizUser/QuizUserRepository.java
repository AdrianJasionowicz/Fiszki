package com.Fiszki.demo.QuizUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizUserRepository extends JpaRepository<QuizUser, Long> {

}
