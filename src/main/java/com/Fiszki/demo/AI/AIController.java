package com.Fiszki.demo.AI;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AIController {
    private AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }


    @PostMapping("/generate-options")
    public AIResponse generateOptions(@RequestBody AIRequest aiRequest) {
        return aiService.generateOptions(
                aiRequest.getQuestion(),
                aiRequest.getCorrectAnswer()
        );
    }
}
