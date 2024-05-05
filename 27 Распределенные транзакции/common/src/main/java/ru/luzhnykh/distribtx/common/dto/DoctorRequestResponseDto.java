package ru.luzhnykh.distribtx.common.dto;

public record DoctorRequestResponseDto(String processId, Integer retCode, String message, String action) {
}
