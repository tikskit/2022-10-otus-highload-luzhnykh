package ru.luzhnykh.distribtx.resources.dto;

/**
 * Dto для отмены резервиварония врача
 * @param requestId ИД заявки
 */
public record DoctorCancelRequestDto (String requestId) {
}
