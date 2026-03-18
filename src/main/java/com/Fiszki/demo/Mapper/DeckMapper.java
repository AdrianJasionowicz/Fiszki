package com.Fiszki.demo.Mapper;

import com.Fiszki.demo.Deck.Deck;
import com.Fiszki.demo.Deck.DeckDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeckMapper {
    Deck toEntity(DeckDTO deckDto);
    DeckDTO toDTO(Deck deck);
}
