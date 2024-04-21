package ru.luzhnykh.distribtx.process.exceptions;

public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException(String message) {
        super(message);
    }
}
