package ru.luzhnykh.distribtx.process.dto;

import ru.luzhnykh.distribtx.process.enums.ProcessState;
import ru.luzhnykh.distribtx.process.enums.StepState;

public record ProcessDto (String requestId, ProcessState state, StepState prescriptionStep, StepState doctorStep,
                          StepState paymentStep, StepState notificationStep) {
}
