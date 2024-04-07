package ru.luzhnykh.distribtx.resources.dto;

import java.time.LocalDate;

/**
 * Запрос на резервирование врача на операцию
 * @param requestId ИД запроса
 * @param doctorId ИД врача
 * @param prescriptionId ИД назначения
 * @param start Дата начала брони врача
 * @param end Дата окончания брони врача
 */
public record DoctorRequestDto(String requestId, String doctorId, String prescriptionId,
                               LocalDate start, LocalDate end) {
}
