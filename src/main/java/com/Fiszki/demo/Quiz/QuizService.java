package com.Fiszki.demo.Quiz;

import com.Fiszki.demo.Deck.Deck;
import com.Fiszki.demo.Deck.DeckRepository;
import com.Fiszki.demo.Exception.*;
import com.Fiszki.demo.FlashCard.Flashcard;
import com.Fiszki.demo.FlashCard.FlashcardRepository;
import com.Fiszki.demo.Mapper.QuizMapper;
import com.Fiszki.demo.Mapper.QuizQuestionMapper;
import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import com.Fiszki.demo.QuizQuestion.QuizQuestionDTO;
import com.Fiszki.demo.QuizQuestion.QuizQuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {

    private final DeckRepository deckRepository;
    private final FlashcardRepository flashcardRepository;
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private QuizQuestionRepository quizQuestionRepository;
    private QuizQuestionMapper quizQuestionMapper;

    public QuizService(DeckRepository deckRepository, FlashcardRepository flashcardRepository, QuizRepository quizRepository, QuizQuestionRepository quizQuestionRepository, QuizMapper quizMapper, QuizQuestionMapper quizQuestionMapper) {
        this.deckRepository = deckRepository;
        this.flashcardRepository = flashcardRepository;
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizMapper = quizMapper;
        this.quizQuestionMapper = quizQuestionMapper;
    }

    @Transactional
    public QuizDTO startQuiz(Long deckId) {
        Deck deck = deckRepository.findById(deckId).orElseThrow(() -> new DeckNotFoundException("Deck not found"));
        List<Flashcard> flashcards = flashcardRepository.findAllByDeckId(deckId);
        Collections.shuffle(flashcards);
        int order = 1;
        if (flashcards.isEmpty()) {
            throw new RuntimeException("Deck nie ma fiszek");
        }
    Quiz quiz = new Quiz();
    quiz.setDeck(deck);
        quiz.setTotalQuestions(flashcards.size());
    quiz.setScore(0);
    quiz.setStartTime(LocalDateTime.now());
        quiz.setStatus(QuizStatus.IN_PROGRESS);
        quizRepository.save(quiz);

        for (Flashcard card : flashcards) {
            QuizQuestion qq = new QuizQuestion();
            qq.setQuiz(quiz);
            qq.setQuestionOrder(order++);
            qq.setFlashcard(card);
            qq.setIsCorrect(null);
            quizQuestionRepository.save(qq);
        }

 return quizMapper.toDTO(quiz);
}

    public QuizQuestionDTO getNextQuestion(Long quizId) {
        return quizQuestionRepository
                .findFirstByQuizIdAndIsCorrectIsNull(quizId)
                .map(quizQuestionMapper::toDTO)
                .orElse(null);
    }

    @Transactional
    public void answerQuestion(Long quizQuestionId, String answer) {
        QuizQuestion question = quizQuestionRepository.findById(quizQuestionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        if (question.getQuiz().getStatus() == QuizStatus.FINISHED) {
            throw new QuizAlreadyFinishedException("Quiz is finished");
        }
        if (question.getChosenAnswer() != null) {
            throw new QuestionAlreadyAnsweredException("Question already answered");
        }
        boolean correctAnswer = answer != null &&
                answer.equals(question.getFlashcard().getCorrectAnswer());
        Quiz quiz = question.getQuiz();

        question.setChosenAnswer(answer);
        question.setIsCorrect(correctAnswer);

        if (correctAnswer) {
            quiz.setScore(quiz.getScore() + 1);
        }

        quizRepository.save(quiz);
        quizQuestionRepository.save(question);
    }

    @Transactional
    public QuizDTO finishQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

        double percent = calculatePercentage(quiz);
        quiz.setPercentage(percent);
        quiz.setFinishTime(LocalDateTime.now());
        quiz.setStatus(QuizStatus.FINISHED);
        quizRepository.save(quiz);

        return quizMapper.toDTO(quiz);
    }

    public double calculatePercentage(Quiz quiz) {
        double score = (double) quiz.getScore();
        double questions = (double) quiz.getTotalQuestions();
        if (((score / questions) * 100) < 0.1) {
            return 0;
        }
        return (score / questions) * 100;
    }


    public QuizDTO getResult(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
        return quizMapper.toDTO(quiz);
    }
}
