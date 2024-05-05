package ru.luzhnykh.distribtx.common.domain;

import ru.luzhnykh.distribtx.common.enums.DoctorRequestState;

import java.time.LocalDate;

public record DoctorRequest(String processId, String doctorId, String prescriptionId,
                            LocalDate start, LocalDate end, DoctorRequestState state) {
}
