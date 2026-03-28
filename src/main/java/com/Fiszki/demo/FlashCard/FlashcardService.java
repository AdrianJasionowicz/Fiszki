package com.Fiszki.demo.FlashCard;

import com.Fiszki.demo.Deck.Deck;
import com.Fiszki.demo.Deck.DeckDTO;
import com.Fiszki.demo.Deck.DeckRepository;
import com.Fiszki.demo.Exception.FlashcardNotFoundException;
import com.Fiszki.demo.Mapper.DeckMapper;
import com.Fiszki.demo.Mapper.FlashcardMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlashcardService {

    private final DeckMapper deckMapper;
    private FlashcardRepository flashcardRepository;
    private FlashcardMapper flashcardMapper;
    private DeckRepository deckRepository;

    public FlashcardService(FlashcardRepository flashcardRepository, FlashcardMapper flashcardMapper, DeckMapper deckMapper, DeckRepository deckRepository) {
        this.flashcardRepository = flashcardRepository;
        this.flashcardMapper = flashcardMapper;
        this.deckMapper = deckMapper;
        this.deckRepository = deckRepository;
    }

    public void addFlashcard(FlashcardDTO flashcard) {
        Deck deck = deckRepository.findById(flashcard.getDeckId())
                .orElseThrow(() -> new RuntimeException("Deck not found"));
        Flashcard flashcardz = flashcardMapper.toEntity(flashcard);
        flashcardz.setOptions(flashcard.getOptions());
        flashcardz.setDeck(deck);

        flashcardRepository.save(flashcardz);
    }

    public void deleteFlashcard(Long id) {
        if (id != null) {
            flashcardRepository.deleteById(id);
        }
    }

    public FlashcardDTO getFlashcard(Long id) {
        return flashcardRepository.findById(id)
                .map(flashcardMapper::toDto)
                .orElse(null);
    }

    public List<FlashcardDTO> getAllFlashcardsByDeckId(Long deckId) {
      List<Flashcard> flashcards = flashcardRepository.findAllByDeckId(deckId);

      return flashcards
              .stream()
              .map(flashcardMapper::toDto)
              .collect(Collectors.toList());
    }

    public FlashcardDTO updateFlashcard(FlashcardDTO flashcard) {
        Flashcard card = flashcardRepository.findById(flashcard.getId())
                .orElseThrow(() -> new FlashcardNotFoundException("Flashcard not found"));
        if (card != null) {
            if (flashcard.getQuestion() != null) {
                card.setQuestion(flashcard.getQuestion());
            }
            if (flashcard.getOptions() != null) {
                card.setOptions(flashcard.getOptions());
            }
            if (flashcard.getCorrectAnswer() != null) {
                card.setCorrectAnswer(flashcard.getCorrectAnswer());
            }
            flashcardRepository.save(card);
        }
        return flashcardMapper.toDto(card);
    }


}
