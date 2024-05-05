package ru.luzhnykh.distribtx.process.service;


import ru.luzhnykh.distribtx.process.dto.StartProcessParamDto;

/**
 * Сервис, который выполняет оркестрацию процесса
 */
public interface ProcessOrchestratorService {
    void startProcess(StartProcessParamDto param);
    void move(String processId);
}
