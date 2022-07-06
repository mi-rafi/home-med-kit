package com.github.mirafi.homemedkit.exception;

public class TelegramServerException extends RuntimeException {

    private final String translation;

    public TelegramServerException(String translation) {
        super();
        this.translation = translation;
    }

    public TelegramServerException(String message, String translation) {
        super(message);
        this.translation = translation;
    }

    public TelegramServerException(String message, String translation, Throwable cause) {
        super(message, cause);
        this.translation = translation;
    }
}
