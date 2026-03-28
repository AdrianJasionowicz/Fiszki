package com.Fiszki.demo.Quiz;

import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    void deleteByDeck_Id(Long deckId);
}
