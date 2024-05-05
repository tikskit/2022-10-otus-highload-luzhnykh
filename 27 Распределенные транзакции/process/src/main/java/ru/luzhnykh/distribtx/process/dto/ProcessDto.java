package ru.luzhnykh.distribtx.process.dto;

import ru.luzhnykh.distribtx.process.enums.ProcessExecutionState;
import ru.luzhnykh.distribtx.process.enums.StepState;

/**
 * Dto содержит информацию о том, в какой точке находится процесс сейчас, как выполнились предыдущие шаги и текущий статус
 * следующих
 * @param processId ИД процесса
 * @param startParams Параметры процесса, с которыми он был запущен
 * @param state Статус выполнения процесса
 * @param prescriptionStep Статус выполнения шага изменения назначения
 * @param doctorStep Статус выполнения шага резервирования врача
 * @param paymentStep Статус выполнения шага оплаты операции
 * @param notificationStep Статус выполнения шага рассылки уведомления
 */
public record ProcessDto (String processId,
                          StartProcessParamDto startParams,
                          ProcessExecutionState state,
                          StepState prescriptionStep,
                          StepState doctorStep,
                          StepState paymentStep, StepState notificationStep) {
}
