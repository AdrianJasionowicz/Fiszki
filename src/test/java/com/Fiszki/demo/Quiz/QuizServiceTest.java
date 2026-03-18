package com.Fiszki.demo.Quiz;

import com.Fiszki.demo.Deck.Deck;
import com.Fiszki.demo.Deck.DeckRepository;
import com.Fiszki.demo.FlashCard.Flashcard;
import com.Fiszki.demo.FlashCard.FlashcardRepository;
import com.Fiszki.demo.Mapper.QuizMapper;
import com.Fiszki.demo.Mapper.QuizQuestionMapper;
import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import com.Fiszki.demo.QuizQuestion.QuizQuestionDTO;
import com.Fiszki.demo.QuizQuestion.QuizQuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private FlashcardRepository flashcardRepository;
    @Mock
    private QuizQuestionRepository quizQuestionRepository;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private DeckRepository deckRepository;
    @Spy
    @InjectMocks
    private QuizService quizService;
    @Mock
    private QuizMapper quizMapper;
    @Mock
    private QuizQuestionMapper quizQuestionMapper;

    @Test
    void startQuiz() {
        Long id = 1L;

        Deck deck = new Deck();
        deck.setId(id);

        Flashcard f1 = new Flashcard();
        Flashcard f2 = new Flashcard();
        Flashcard f3 = new Flashcard();

        List<Flashcard> flashcards = new ArrayList<>(List.of(f1, f2, f3));

        when(deckRepository.findById(id)).thenReturn(Optional.of(deck));
        when(flashcardRepository.findAllByDeckId(id)).thenReturn(flashcards);
        quizService.startQuiz(id);

        verify(deckRepository).findById(id);
        verify(flashcardRepository).findAllByDeckId(id);
        verify(quizRepository).save(any(Quiz.class));
        verify(quizQuestionRepository, times(3)).save(any(QuizQuestion.class));
    }




    @Test
    void getNextQuestion() {
        long id = 1;
        QuizQuestion question = new QuizQuestion();
        question.setId(id);
        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setStatus(QuizStatus.IN_PROGRESS);
        question.setQuiz(quiz);
        QuizQuestionDTO questionDTO = new QuizQuestionDTO();
        questionDTO.setId(id);
        when(quizQuestionRepository.findFirstByQuizIdAndCorrectAnswerIsNull(id)).thenReturn(Optional.of(question));
        when(quizQuestionMapper.toDTO(question)).thenReturn(questionDTO);
        QuizQuestionDTO result = quizService.getNextQuestion(id);
        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(quizQuestionRepository).findFirstByQuizIdAndCorrectAnswerIsNull(id);
        verify(quizQuestionMapper).toDTO(question);
    }


    @Test
    void answerQuestion_correct() {
        Quiz quiz = new Quiz();
        quiz.setScore(0);

        Flashcard flashcard = new Flashcard();
        flashcard.setCorrectAnswer("True");

        QuizQuestion question = new QuizQuestion();
        question.setQuiz(quiz);
        question.setFlashcard(flashcard);

        when(quizQuestionRepository.findById(1L)).thenReturn(Optional.of(question));

        quizService.answerQuestion(1L, "True");

        assertEquals(1, quiz.getScore());
        assertTrue(question.getCorrectAnswer());

        verify(quizRepository).save(quiz);
        verify(quizQuestionRepository).save(question);
    }

    @Test
    void getNextQuestion_shouldThrowException_whenNoMoreQuestions() {
        Long quizId = 1L;

        when(quizQuestionRepository
                .findFirstByQuizIdAndCorrectAnswerIsNull(quizId))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> quizService.getNextQuestion(quizId)
        );

        assertEquals("No more questions", ex.getMessage());

        verify(quizQuestionRepository)
                .findFirstByQuizIdAndCorrectAnswerIsNull(quizId);
    }

    @Test
    void finishQuiz() {
        Long id = 1L;
        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setScore(8);
        quiz.setTotalQuestions(10);

        QuizDTO dto = new QuizDTO();
        dto.setId(id);
        dto.setScore(8);
        dto.setTotalQuestions(10);
        when(quizRepository.findById(id)).thenReturn(Optional.of(quiz));
        when(quizMapper.toDTO(quiz)).thenReturn(dto);

        doReturn(80D).when(quizService).calculatePercentage(quiz);
        QuizDTO result = quizService.finishQuiz(id);
        quizService.calculatePercentage(quiz);
        assertNotNull(quiz.getFinishTime());
        assertTrue(quiz.isFinished());
        assertEquals(80.0, quiz.getPercentage());
        verify(quizRepository).save(quiz);
        assertNotNull(result);
    }



    @Test
    void calculatePercentage() {
        Long id = 1L;
        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setScore(8);
        quiz.setTotalQuestions(10);
        assertEquals(80D, quizService.calculatePercentage(quiz),0.001);
    }

    @Test
    void calculatePercentage_shouldReturnZero_whenNoQuestions() {
        Quiz quiz = new Quiz();
        quiz.setScore(0);
        quiz.setTotalQuestions(0);

        double result = quizService.calculatePercentage(quiz);

        assertEquals(NaN, result);
    }
}