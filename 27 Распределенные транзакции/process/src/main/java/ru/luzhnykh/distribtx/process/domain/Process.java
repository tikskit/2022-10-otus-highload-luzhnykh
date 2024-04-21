package ru.luzhnykh.distribtx.process.domain;

public record Process(String requestId, String state, String prescriptionStep, String doctorStep, String paymentStep,
                      String notificationStep) {
}
