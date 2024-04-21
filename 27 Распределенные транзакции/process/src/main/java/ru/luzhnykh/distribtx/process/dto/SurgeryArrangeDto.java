package ru.luzhnykh.distribtx.process.dto;

import java.time.LocalDate;

/**
 * Dto, запускающее процесс госпитализации пациента для проведения операции
 * @param prescriptionId ИД назанчения
 * @param doctorId ИД хирурга
 * @param start дата начала операции
 * @param end дата окончания операции
 */
public record SurgeryDto(String prescriptionId, String doctorId, LocalDate start, LocalDate end) {
}
