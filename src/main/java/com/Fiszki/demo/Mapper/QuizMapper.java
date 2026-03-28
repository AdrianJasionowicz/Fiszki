    package com.Fiszki.demo.Mapper;

    import com.Fiszki.demo.Quiz.Quiz;
    import com.Fiszki.demo.Quiz.QuizDTO;
    import org.mapstruct.Mapper;

    @Mapper(componentModel = "spring")
    public interface QuizMapper {
        Quiz toEntity(QuizDTO quizDTO);
        QuizDTO toDTO(Quiz quiz);
    }
