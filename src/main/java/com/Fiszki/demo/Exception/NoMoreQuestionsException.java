package com.Fiszki.demo.Exception;

public class NoMoreQuestionsException extends RuntimeException {
    public NoMoreQuestionsException(String message) {
        super(message);
    }
}
