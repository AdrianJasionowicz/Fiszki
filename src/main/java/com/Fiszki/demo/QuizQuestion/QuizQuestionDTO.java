package com.Fiszki.demo.QuizQuestion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizQuestionDTO {
    private Long id;
    private Long quizId;
    private String question;
    private List<String> options = new ArrayList<>();

}
