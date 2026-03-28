package com.Fiszki.demo.Quiz;

import com.Fiszki.demo.QuizQuestion.QuizQuestionDTO;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/quiz/{quizId}/next")
    public ResponseEntity<?> getNextQuestion(@PathVariable Long quizId) {
        QuizQuestionDTO dto = quizService.getNextQuestion(quizId);
        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/quiz/answer")
    public ResponseEntity<Void> answerQuiz(@RequestBody AnswerRequest request) {

        quizService.answerQuestion(
                request.getQuestionId(),
                request.getChosenAnswer()
        );
        return ResponseEntity.ok().build();
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

