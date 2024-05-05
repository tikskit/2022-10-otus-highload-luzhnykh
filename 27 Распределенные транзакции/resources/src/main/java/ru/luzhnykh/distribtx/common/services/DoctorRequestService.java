package ru.luzhnykh.distribtx.common.services;


import ru.luzhnykh.distribtx.common.dto.DoctorRequestDto;

/**
 * Сервис резервирования врачей
 */
public interface DoctorRequestService {
    void addRequest(DoctorRequestDto requestDto);
    void cancelRequest(DoctorRequestDto requestDto);
}
