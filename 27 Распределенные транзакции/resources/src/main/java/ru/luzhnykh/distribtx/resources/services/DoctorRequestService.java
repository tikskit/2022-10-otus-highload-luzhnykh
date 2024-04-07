package ru.luzhnykh.distribtx.resources.services;

import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestResponseDto;

/**
 * Сервис резервирования врачей
 */
public interface DoctorRequestService {
    void addRequest(DoctorRequestDto requestDto);
    void cancelRequest(DoctorRequestDto requestDto);
}
