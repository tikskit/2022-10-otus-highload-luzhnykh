package ru.luzhnykh.distribtx.process.enums;

public enum StepState {
    // Шаг запланирован, но ещё не выполнялся
    PLANNED,
    // Шаг в процессе выполнения
    EXECUTING,
    // Шаг в процессе отмены
    ROLLING_BACK,
    // Шаг успешнот выполнен
    DONE,
    // Шаг успешно отменен
    CANCELLED
}
