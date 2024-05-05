package ru.luzhnykh.distribtx.process.enums;

/**
 * Статус выполнения процесса
 */
public enum ProcessExecutionState {
    // Процесс выполняется
    EXECUTING,
    // Происходит откат процесса назад
    ROLLING_BACK,
    // Успешно завершен
    DONE,
    // Откат успешно завершен
    CANCELLED;
}
