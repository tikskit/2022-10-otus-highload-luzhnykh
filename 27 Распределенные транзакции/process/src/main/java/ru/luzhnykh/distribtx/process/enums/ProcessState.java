package ru.luzhnykh.distribtx.process.enums;

public enum ProcessState {
    // Процесс выполняется
    EXECUTING,
    // Происходит откат процесса назад
    ROLLING_BACK,
    // Успешно завершен
    DONE,
    // Откат успешно завершен
    CANCELLED;
}
