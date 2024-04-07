package ru.luzhnykh.distribtx.resources.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DoctorRequestRetCodes {
    SUCCESS(1),
    BUSY(2),
    EXCEPTION(3);

    private final int code;

}
