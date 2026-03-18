package com.Fiszki.demo.Quiz;

import com.Fiszki.demo.QuizQuestion.QuizQuestionDTO;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {

    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/quiz/start/{deckId}")
    public QuizDTO startQuiz(@PathVariable Long deckId) {
     return quizService.startQuiz(deckId);
    }

    @GetMapping("/quiz//{quizId}/next")
    public QuizQuestionDTO getNextQuestion(@PathVariable Long quizId) {
        return quizService.getNextQuestion(quizId);
    }

    @PostMapping("/quiz/answer")
    public void answerQuiz(@RequestBody AnswerRequest request) {

        quizService.answerQuestion(
                request.getQuestionId(),
                request.getChosenAnswer()
        );
    }

    @PostMapping("/quiz/finish/{quizId}")
    public QuizDTO finishQuiz(@PathVariable Long quizId) {
      return   quizService.finishQuiz(quizId);
    }

    @GetMapping("/quiz/{quizId}/result")
    public QuizDTO getResult(@PathVariable Long quizId) {
       return quizService.getResult(quizId);
    }

}

