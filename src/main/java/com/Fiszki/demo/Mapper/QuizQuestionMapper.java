package com.Fiszki.demo.Mapper;

import com.Fiszki.demo.FlashCard.Flashcard;
import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import com.Fiszki.demo.QuizQuestion.QuizQuestionDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class QuizQuestionMapper {

    public QuizQuestionDTO toDTO(QuizQuestion qq) {
        Flashcard card = qq.getFlashcard();

        List<String> options = new ArrayList<>();

        options.add(card.getCorrectAnswer());

        if (card.getOptions() != null) {
            options.addAll(card.getOptions());
        }

        Collections.shuffle(options);

        return new QuizQuestionDTO(
                qq.getId(),
                qq.getQuiz().getId(),
                card.getQuestion(),
                options
        );
    }
}