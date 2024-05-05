package ru.luzhnykh.distribtx.process.dto;

import java.time.LocalDateTime;

/**
 * Dto, запускающее процесс госпитализации пациента для проведения операции
 * @param prescriptionId ИД назанчения
 * @param doctorId ИД хирурга
 * @param start дата и время начала операции
 * @param end дата и время окончания операции
 */
public record SurgeryArrangeDto(String prescriptionId, String doctorId, LocalDateTime start, LocalDateTime end) {
}
