package ru.luzhnykh.distribtx.process.exceptions;

public class ProcessNotFoundException extends RuntimeException {

    public ProcessNotFoundException(String message) {
        super(message);
    }
}
