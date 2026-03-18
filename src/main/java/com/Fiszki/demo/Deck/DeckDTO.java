package com.Fiszki.demo.Deck;

import com.Fiszki.demo.FlashCard.Flashcard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeckDTO {

    private Long id;
    private String name;
    private String description;
    private List<Flashcard> flashcards = new ArrayList<>();

}
