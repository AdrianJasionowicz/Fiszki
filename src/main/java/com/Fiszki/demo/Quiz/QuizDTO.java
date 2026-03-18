package com.Fiszki.demo.Quiz;

import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import com.Fiszki.demo.QuizQuestion.QuizQuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class QuizDTO {
    private Long id;
    private Long deckId;
    private Integer score;
    private Integer totalQuestions;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private QuizStatus quizStatus;
    private double percentage;
}
