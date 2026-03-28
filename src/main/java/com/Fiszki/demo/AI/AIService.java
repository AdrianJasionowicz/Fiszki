package com.Fiszki.demo.AI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AIResponse generateOptions(String question, String correctAnswer) {
        try {
            String url = "https://api.openai.com/v1/responses";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey.trim());

            String prompt = """
                    Wygeneruj dokładnie 3 błędne odpowiedzi do pytania quizowego.
                    Każda odpowiedź ma być realistyczna i podobna do poprawnej.
                    
                    Pytanie: %s
                    Poprawna odpowiedź: %s
                    
                    Zwróć odpowiedzi w formacie:
                    odp1|odp2|odp3
                    """.formatted(question, correctAnswer);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-4.1-mini");
            body.put("input", prompt);
            body.put("max_output_tokens", 100);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            String response = restTemplate.postForObject(url, request, String.class);

            JsonNode root = objectMapper.readTree(response);

            String content = root
                    .path("output")
                    .get(0)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();

            String[] parts = content.split("\\|");

            List<String> wrongAnswers = Arrays.stream(parts)
                    .map(String::trim)
                    .toList();

            return new AIResponse(wrongAnswers);

        } catch (Exception e) {
            throw new RuntimeException("AI error: " + e.getMessage());
        }
    }
}