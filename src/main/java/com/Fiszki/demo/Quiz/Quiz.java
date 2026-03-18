package com.Fiszki.demo.Quiz;

import com.Fiszki.demo.Deck.Deck;
import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Deck deck;
    private Integer score;
    private Integer totalQuestions;
    private LocalDateTime startTime;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizQuestion> questions = new ArrayList<>();
    private double percentage;
    private boolean finished;
    private LocalDateTime finishTime;
    private QuizStatus status;


    public void addQuestion(QuizQuestion q) {
        questions.add(q);
        q.setQuiz(this);
    }
}
