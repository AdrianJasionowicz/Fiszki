package com.Fiszki.demo.QuizQuestion;

import com.Fiszki.demo.FlashCard.Flashcard;
import com.Fiszki.demo.Quiz.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "flashcard_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Flashcard flashcard;

    private int questionOrder;
    private String chosenAnswer;
    private Boolean isCorrect;
}

