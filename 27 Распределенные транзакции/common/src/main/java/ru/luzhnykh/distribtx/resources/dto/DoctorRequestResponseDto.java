package ru.luzhnykh.distribtx.dto.resources;

public record DoctorRequestResponseDto(String requestId, Integer retCode, String message, String action) {
}
