package ru.luzhnykh.distribtx.common.dto;

import java.time.LocalDateTime;

/**
 * Запрос на резервирование врача на операцию
 * @param processId Сквозной ИД процесса
 * @param doctorId ИД врача
 * @param prescriptionId ИД назначения
 * @param start Дата начала брони врача
 * @param end Дата окончания брони врача
 * @param action Действие с заявкой. Допустимые значения 'ARRANGE' и 'CANCEL'
 */
public record DoctorRequestDto(String processId, String doctorId, String prescriptionId,
                               LocalDateTime start, LocalDateTime end, String action) {
}
