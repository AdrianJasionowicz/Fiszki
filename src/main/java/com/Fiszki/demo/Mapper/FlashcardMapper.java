package com.Fiszki.demo.Mapper;

import com.Fiszki.demo.FlashCard.Flashcard;
import com.Fiszki.demo.FlashCard.FlashcardDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlashcardMapper {
    Flashcard toEntity(FlashcardDTO flashcardDto);
    FlashcardDTO toDto(Flashcard flashcard);
}
