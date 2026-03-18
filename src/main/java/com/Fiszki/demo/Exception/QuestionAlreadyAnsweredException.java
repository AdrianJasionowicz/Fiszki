package com.Fiszki.demo.Exception;

public class QuestionAlreadyAnsweredException extends RuntimeException {
    public QuestionAlreadyAnsweredException(String message) {
        super(message);
    }
}
