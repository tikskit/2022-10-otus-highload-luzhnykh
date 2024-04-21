package ru.luzhnykh.distribtx.process.service;

public interface ProcessService {
    void startProcess();
    void move(String requestId);
}
