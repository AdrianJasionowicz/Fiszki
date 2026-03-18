package com.Fiszki.demo.Exception;

public class QuizAlreadyFinishedException extends RuntimeException {
    public QuizAlreadyFinishedException(String message) {
        super(message);
    }
}
