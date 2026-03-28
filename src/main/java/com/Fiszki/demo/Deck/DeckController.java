package com.Fiszki.demo.Deck;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeckController {

    private final DeckService deckService;


    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @DeleteMapping("/decks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@deckService.isOwner(#id, authentication)")
    public void deleteDeck(@PathVariable Long id) {



        deckService.deleteDeckById(id);
    }

    @GetMapping("/decks/{id}")
    @PreAuthorize("@deckService.isOwner(#id, authentication)")
    public DeckDTO getDeck(@PathVariable Long id) {
       return deckService.getDeckById(id);
    }

    @GetMapping("/decks")
    public List<DeckDTO> getDecksByUserId(Authentication authentication) {
        return deckService.getDecksByUserToken(authentication);
    }

    @PostMapping("/decks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createDeck(@RequestBody DeckDTO deck, Authentication authentication) {

        deckService.addDeck(deck, authentication);
    }

    @PutMapping("/decks/{id}")
    @PreAuthorize("@deckService.isOwner(#id, authentication)")
    @ResponseStatus(HttpStatus.OK)
    public void updateDeck(@PathVariable Long id, @RequestBody DeckDTO deck) {

        deck.setId(id);
        deckService.updateDeck(deck);

    }


}
