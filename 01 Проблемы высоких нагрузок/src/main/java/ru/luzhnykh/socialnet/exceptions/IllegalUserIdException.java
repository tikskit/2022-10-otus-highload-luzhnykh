package ru.luzhnykh.socialnet.exceptions;

public class IllegalUserIdException extends RuntimeException{
    public IllegalUserIdException(String message) {
        super(message);
    }
}
