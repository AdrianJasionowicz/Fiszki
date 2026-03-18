package com.Fiszki.demo.FlashCard;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlashCardController {

    private FlashcardService flashcardService;

    public FlashCardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping("/flashcards")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFlashCard(@RequestBody FlashcardDTO flashcard) {
        flashcardService.addFlashcard(flashcard);
    }

    @DeleteMapping("/flashcards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFlashCard(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
    }

    @PutMapping("/flashcards/{id}")
    public void updateFlashCard(@PathVariable Long id, @RequestBody FlashcardDTO flashcard) {
        flashcard.setId(id);
        flashcardService.updateFlashcard(flashcard);


    }

    @GetMapping("/flashcards/{id}")
    public FlashcardDTO getFlashCard(@PathVariable Long id) {
        return flashcardService.getFlashcard(id);
    }

    @GetMapping("/flashcards/deck/{id}")
    public List<FlashcardDTO> getAllFlashCardsByDeck(@PathVariable Long id) {
       return flashcardService.getAllFlashcardsByDeckId(id);
    }

}
