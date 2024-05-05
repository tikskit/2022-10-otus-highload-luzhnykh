package ru.luzhnykh.distribtx.common.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DoctorRequestRetCodes {
    SUCCESS(0),
    BUSY(1),
    EXCEPTION(2);

    private final Integer code;

}
