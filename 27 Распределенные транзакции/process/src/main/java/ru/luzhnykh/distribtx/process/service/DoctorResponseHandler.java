package ru.luzhnykh.distribtx.process.service;

import ru.luzhnykh.distribtx.common.dto.DoctorRequestResponseDto;

/**
 * Обработчик события ответа на запрос регистрации врача
 */
public interface DoctorResponseHandler {
    /**
     * Обработать ответ
     */
    void handle(DoctorRequestResponseDto responseDto);
}
