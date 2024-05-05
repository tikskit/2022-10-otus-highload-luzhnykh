package ru.luzhnykh.distribtx.process.domain;

/**
 * Доменная сущсность процесса
 * @param processId ИД процесса
 * @param params параметры, с которыми был задан процесс (содержится JSON)
 * @param state Статус выполнения процесса
 * @param prescriptionStep Статус выполнения шага изменения назначения
 * @param doctorStep Статус выполнения шага резервирования врача
 * @param paymentStep Статус выполнения шага оплаты операции
 * @param notificationStep Статус выполнения шага рассылки уведомления
 * @param notificationStep Статус выполнения шага рассылки уведомления
 */
public record Process(String processId, String params, String state, String prescriptionStep, String doctorStep, String paymentStep,
                      String notificationStep) {
}
