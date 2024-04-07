package ru.luzhnykh.distribtx.resources.domain;

import ru.luzhnykh.distribtx.resources.enums.DoctorRequestState;

import java.time.LocalDate;

public record DoctorRequest(String requestId, String doctorId, String prescriptionId,
                            LocalDate start, LocalDate end, DoctorRequestState state) {
}
