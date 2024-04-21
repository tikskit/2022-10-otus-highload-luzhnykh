package ru.luzhnykh.distribtx.resources.services;


import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;

/**
 * Сервис резервирования врачей
 */
public interface DoctorRequestService {
    void addRequest(DoctorRequestDto requestDto);
    void cancelRequest(DoctorRequestDto requestDto);
}
