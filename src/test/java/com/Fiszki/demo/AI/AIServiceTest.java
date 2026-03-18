package com.Fiszki.demo.AI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AIServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AIService aiService;

    @Test
    void generateOptions() {

        String fakeResponse = "Kraków, Gdańsk, Poznań";

        when(restTemplate.postForObject(
                anyString(),
                any(),
                eq(String.class)
        )).thenReturn(fakeResponse);

        String result = aiService.generatedOptions(
                "Jaka jest stolica Polski?",
                "Warszawa"
        );

        assertEquals(fakeResponse, result);
    }
}