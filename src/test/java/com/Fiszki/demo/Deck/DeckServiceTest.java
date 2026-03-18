package com.Fiszki.demo.Deck;

import com.Fiszki.demo.Mapper.DeckMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class DeckServiceTest {

    @Mock
    private DeckRepository deckRepository;
    @Mock
    private DeckMapper deckMapper;
    @InjectMocks
    private DeckService deckService;

    @Test
    void deleteDeckById() {
        Long id = 50L;
        deckService.deleteDeckById(id);
        verify(deckRepository).deleteById(id);
    }

    @Test
    void getDeckById() {
        Long id = 50L;

        Deck deck = new Deck();
        deck.setId(id);

        DeckDTO deckDto = new DeckDTO();
        deckDto.setId(id);

        when(deckRepository.findById(id)).thenReturn(Optional.of(deck));
        when(deckMapper.toDTO(deck)).thenReturn(deckDto);

        DeckDTO result = deckService.getDeckById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(deckRepository).findById(id);
        verify(deckMapper).toDTO(deck);
    }

    @Test
    void addDeck() {
        DeckDTO deckDto = new DeckDTO();
        deckDto.setDescription("Test Deck");

        Deck deck = new Deck();
        deck.setDescription("Test Deck");

        when(deckMapper.toEntity(deckDto)).thenReturn(deck);
        deckService.addDeck(deckDto);
        verify(deckRepository).save(deck);
    }

    @Test
    void updateDeck() {
        Long id = 100L;
        Deck entity = new Deck();
        entity.setId(id);
        entity.setDescription("Test Deck");
        entity.setName("Test Deck");

        DeckDTO deckDto = new DeckDTO();
        deckDto.setId(id);
        deckDto.setDescription("Test Deck");
        deckDto.setName("Test Deck");

        DeckDTO newDeckDto = new DeckDTO();
        newDeckDto.setId(id);
        newDeckDto.setDescription("New Test Deck");
        newDeckDto.setName("New Test Deck");

        when(deckRepository.findById(id)).thenReturn(Optional.of(entity));
        when(deckMapper.toDTO(entity)).thenReturn(newDeckDto);

        DeckDTO deckResult = deckService.updateDeck(newDeckDto);
        assertEquals("New Test Deck", deckResult.getName());
        assertEquals("New Test Deck", deckResult.getDescription());
        verify(deckRepository).findById(id);
        verify(deckMapper).toDTO(entity);
        verify(deckRepository).save(entity);
        assertNotNull(deckResult);
    }

}