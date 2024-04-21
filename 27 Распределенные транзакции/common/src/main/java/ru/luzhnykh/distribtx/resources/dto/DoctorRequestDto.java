package ru.luzhnykh.distribtx.dto.resources;

import java.time.LocalDate;

/**
 * Запрос на резервирование врача на операцию
 * @param requestId Сквозной ИД запроса
 * @param doctorId ИД врача
 * @param prescriptionId ИД назначения
 * @param start Дата начала брони врача
 * @param end Дата окончания брони врача
 * @param action Действие с заявкой. Допустимые значения 'ARRANGE' и 'CANCEL'
 */
public record DoctorRequestDto(String requestId, String doctorId, String prescriptionId,
                               LocalDate start, LocalDate end, String action) {
}
