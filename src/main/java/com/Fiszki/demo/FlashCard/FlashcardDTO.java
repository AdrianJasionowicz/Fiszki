package com.Fiszki.demo.FlashCard;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FlashcardDTO {

    private Long id;
    private String question;
    private String correctAnswer;
    private List<String> options;
    private Long deckId;

}
