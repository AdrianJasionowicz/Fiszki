package com.Fiszki.demo.FlashCard;

import com.Fiszki.demo.Deck.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    List<Flashcard> findAllByDeckId(Long deckId);
}
