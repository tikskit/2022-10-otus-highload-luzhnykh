package ru.luzhnykh.distribtx.process.service;

import ru.luzhnykh.distribtx.process.dto.ProcessDto;

import java.util.Optional;

/**
 * Сервис для работы с процессом
 */
public interface ProcessService {
    /**
     * Получить процесс по ИД
     * @param processId ИД процесса
     */
    Optional<ProcessDto> getById(String processId);

    /**
     * Обновить процесс в БД
     */
    void update(ProcessDto processDto);
}
