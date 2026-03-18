package com.Fiszki.demo.Mapper;


import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import com.Fiszki.demo.QuizQuestion.QuizQuestionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface QuizQuestionMapper {
    QuizQuestion toEntity(QuizQuestionDTO quizQuestionDTO);
    QuizQuestionDTO toDTO(QuizQuestion quizQuestion);


}
