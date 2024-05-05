package ru.luzhnykh.distribtx.process.dto;

import java.time.LocalDateTime;


/**
 * Параметры для запуска процесса
 * @param prescriptionId ИД назначения
 * @param doctorId ИД врача, выполняющего операцию
 * @param start Дата и время начала операции
 * @param end Дата и время окончания операции
 */
public record StartProcessParamDto(String prescriptionId, String doctorId,
                                   LocalDateTime start,
                                   LocalDateTime end
) {
}
