package com.Fiszki.demo.AI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

        @Value("${openai.api.key}")
        private String apiKey;

    private final RestTemplate restTemplate;

    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String generateOptions(String question, String correctAnswer) {

            String url = "https://api.openai.com/v1/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String prompt = """
                    Wygeneruj 3 niepoprawne odpowiedzi do pytania quizowego.
                    
                    Pytanie: %s
                    Poprawna odpowiedź: %s
                    
                    Zwróć tylko odpowiedzi oddzielone przecinkami.
                    """.formatted(question, correctAnswer);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-4.1-mini");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "user", "content", prompt));

            body.put("messages", messages);
            body.put("max_tokens", 100);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            return restTemplate.postForObject(url, request, String.class);
        }
    }