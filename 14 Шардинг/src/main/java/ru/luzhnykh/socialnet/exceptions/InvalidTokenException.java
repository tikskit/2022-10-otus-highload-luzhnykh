package ru.luzhnykh.socialnet.exceptions;

/**
 * Выбрасывается, когда передан некорректный токер
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
