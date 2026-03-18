package com.Fiszki.demo.FlashCard;

import com.Fiszki.demo.Deck.Deck;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String correctAnswer;
    @ElementCollection
    private List<String> options = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "deck_id")
    private Deck deck;

}
