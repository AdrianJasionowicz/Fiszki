package com.Fiszki.demo.FlashCard;

import com.Fiszki.demo.Deck.Deck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.Fiszki.demo.Mapper.FlashcardMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlashcardServiceTest {

    @Mock
    private FlashcardRepository flashcardRepository;

    @Mock
    private FlashcardMapper flashcardMapper;

    @InjectMocks
    private FlashcardService flashcardService;

    @Test
    void addFlashcard() {
        FlashcardDTO dto = new FlashcardDTO();
        dto.setQuestion("What is the question?");

        Flashcard entity = new Flashcard();
        entity.setQuestion("What is the question?");

        when(flashcardMapper.toEntity(dto)).thenReturn(entity);

        flashcardService.addFlashcard(dto);

        verify(flashcardRepository).save(entity);
    }

    @Test
    void shouldNotSaveWhenDtoIsNull() {
        flashcardService.addFlashcard(null);
        verify(flashcardRepository, never()).save(any());
    }

    @Test
    void deleteFlashcard() {
        Long id = 99L;
        flashcardService.deleteFlashcard(id);
        verify(flashcardRepository).deleteById(id);
    }

    @Test
    void getFlashcard() {
        Long id = 99L;

        Flashcard entity = new Flashcard();
        entity.setId(id);

        FlashcardDTO dto = new FlashcardDTO();
        dto.setId(id);

        when(flashcardRepository.findById(id)).thenReturn(Optional.of(entity));
        when(flashcardMapper.toDto(entity)).thenReturn(dto);

        FlashcardDTO result = flashcardService.getFlashcard(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(flashcardRepository).findById(id);
        verify(flashcardMapper).toDto(entity);
    }
    @Test
    void getFlashcardNULL() {
        Long id = 99L;

        Flashcard entity = new Flashcard();
        entity.setId(id);

        FlashcardDTO dto = new FlashcardDTO();
        dto.setId(id);

        when(flashcardRepository.findById(id)).thenReturn(Optional.of(entity));
        when(flashcardMapper.toDto(entity)).thenReturn(dto);

        FlashcardDTO result = flashcardService.getFlashcard(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(flashcardRepository).findById(id);
        verify(flashcardMapper).toDto(entity);
    }

    @Test
    void updateFlashcard() {
        Long id = 99L;

        Flashcard entity = new Flashcard();
        entity.setId(id);
        entity.setQuestion("Old question");
        entity.setCorrectAnswer("God");

        FlashcardDTO dto = new FlashcardDTO();
        dto.setId(id);
        dto.setQuestion("New question");
        dto.setCorrectAnswer("Dog");

        FlashcardDTO mappedDto = new FlashcardDTO();
        mappedDto.setId(id);
        mappedDto.setQuestion("New question");
        mappedDto.setCorrectAnswer("Dog");

        when(flashcardRepository.findById(id)).thenReturn(Optional.of(entity));
        when(flashcardMapper.toDto(entity)).thenReturn(mappedDto);

        FlashcardDTO result = flashcardService.updateFlashcard(dto);

        assertEquals("New question", entity.getQuestion());
        assertEquals("Dog", entity.getCorrectAnswer());

        verify(flashcardRepository).save(entity);
        assertNotNull(result);
    }
    @Test
    void getAllFlashcardsByDeckId(){
        Long deckId = 1L;

        Flashcard flashcard = new Flashcard();
        Flashcard flashcard2 = new Flashcard();

        FlashcardDTO dto1 = new FlashcardDTO();
        FlashcardDTO dto2 = new FlashcardDTO();

        when(flashcardRepository.findAllByDeckId(deckId)).thenReturn(List.of(flashcard, flashcard2));


        when(flashcardMapper.toDto(flashcard)).thenReturn(dto1);
        when(flashcardMapper.toDto(flashcard2)).thenReturn(dto2);
        List<FlashcardDTO> result = flashcardService.getAllFlashcardsByDeckId(deckId);

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));

        verify(flashcardRepository).findAllByDeckId(deckId);
    }
}