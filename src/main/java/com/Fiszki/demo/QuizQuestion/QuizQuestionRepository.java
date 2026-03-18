package com.Fiszki.demo.QuizQuestion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    Optional<QuizQuestion> findFirstByQuizIdAndCorrectAnswerIsNull(Long quizId);
}
