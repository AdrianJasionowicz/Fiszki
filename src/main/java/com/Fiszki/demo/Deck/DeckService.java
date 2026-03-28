package com.Fiszki.demo.Deck;

import com.Fiszki.demo.Exception.DeckNotFoundException;
import com.Fiszki.demo.FlashCard.Flashcard;
import com.Fiszki.demo.FlashCard.FlashcardRepository;
import com.Fiszki.demo.LoginUser.LoginUser;
import com.Fiszki.demo.LoginUser.LoginUserRepository;
import com.Fiszki.demo.Mapper.DeckMapper;
import com.Fiszki.demo.Quiz.QuizRepository;
import com.Fiszki.demo.QuizQuestion.QuizQuestion;
import com.Fiszki.demo.QuizQuestion.QuizQuestionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeckService {
    private final LoginUserRepository loginUserRepository;
    private final FlashcardRepository flashcardRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;
    private DeckMapper deckMapper;
    private DeckRepository deckRepository;

    public DeckService(DeckMapper deckMapper, DeckRepository deckRepository, LoginUserRepository loginUserRepository, FlashcardRepository flashcardRepository, QuizQuestionRepository quizQuestionRepository, QuizRepository quizRepository) {
        this.deckMapper = deckMapper;
        this.deckRepository = deckRepository;
        this.loginUserRepository = loginUserRepository;
        this.flashcardRepository = flashcardRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizRepository = quizRepository;
    }

    @Transactional
    public void deleteDeckById(Long id) {
        List<Flashcard> flashcards = flashcardRepository.findAllByDeckId(id);
        List<QuizQuestion> questions = quizQuestionRepository.findAll();
        for (Flashcard flashcard : flashcards) {
            quizQuestionRepository.deleteByFlashcard_Id(flashcard.getId());

            flashcardRepository.deleteById(flashcard.getId());
        }
        quizRepository.deleteByDeck_Id(id);
        Deck deck = deckRepository.findById(id)
                .orElseThrow(() -> new DeckNotFoundException("Deck not found"));
        deckRepository.delete(deck);
    }

    public DeckDTO getDeckById(Long id) {
        return deckRepository.findById(id)
                .map(deckMapper::toDTO)
                .orElseThrow(() -> new DeckNotFoundException("Deck not found"));
    }

    public void addDeck(DeckDTO deckDto,Authentication authentication) {
        LoginUser loginUser = getCurrentUser(authentication);

        Deck deck = deckMapper.toEntity(deckDto);
        deck.setQuizUser(loginUser.getQuizUser());

        deckRepository.save(deck);
    }

    public DeckDTO updateDeck(DeckDTO deckDto) {
        Deck deck = deckRepository.findById(deckDto.getId())
                .orElseThrow(() -> new DeckNotFoundException("Deck not found"));

            if (deckDto.getName() != null) {
                deck.setName(deckDto.getName());
            }
            if (deckDto.getDescription() != null) {
                deck.setDescription(deckDto.getDescription());
            }
            if (deckDto.getFlashcards() != null) {
                deck.setFlashcards(deckDto.getFlashcards());
            }
            deckRepository.save(deck);

        return deckMapper.toDTO(deck);
    }

    public List<DeckDTO> getDecksByUserToken(Authentication authentication) {
        LoginUser loginUser = getCurrentUser(authentication);
        Long quizUserID = loginUser.getQuizUser().getId();
        List<Deck> deckList = deckRepository.getDecksByQuizUser_Id(quizUserID);
        return deckList
                .stream()
                .map(deckMapper::toDTO)
                .collect(Collectors.toList());
    }
    public boolean isOwner(Long id, Authentication authentication) {
        return checkDeckOwner(id, authentication);
    }

    private LoginUser getCurrentUser(Authentication authentication) {
        return loginUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean checkDeckOwner(Long id, Authentication authentication) {
        LoginUser loginUser = getCurrentUser(authentication);
        Long quizUserID = loginUser.getQuizUser().getId();
        Deck deck = deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException("Deck not found"));

        Long deckOwnerID = deck.getQuizUser().getId();
        return quizUserID.equals(deckOwnerID);
    }

}
