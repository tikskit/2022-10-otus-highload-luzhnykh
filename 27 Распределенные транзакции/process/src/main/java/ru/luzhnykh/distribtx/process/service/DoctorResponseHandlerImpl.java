package ru.luzhnykh.distribtx.process.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.common.dto.DoctorRequestResponseDto;
import ru.luzhnykh.distribtx.common.enums.Action;
import ru.luzhnykh.distribtx.common.enums.DoctorRequestRetCodes;
import ru.luzhnykh.distribtx.process.dto.ProcessDto;
import ru.luzhnykh.distribtx.process.enums.ProcessExecutionState;
import ru.luzhnykh.distribtx.process.enums.StepState;
import ru.luzhnykh.distribtx.process.exceptions.ProcessNotFoundException;

/**
 * Реализация обработчика события ответа на запрос регистрации врача
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class DoctorResponseHandlerImpl implements DoctorResponseHandler {
    private final ProcessService processService;

    /**
     * Обработать ответ
     */
    @Override
    public void handle(DoctorRequestResponseDto responseDto) {
        ProcessDto processDto = null;
        try {
            processDto = processService.getById(responseDto.processId())
                    .orElseThrow(() -> new ProcessNotFoundException(String.format("Процесс %s не найден", responseDto.processId())));

            if (DoctorRequestRetCodes.SUCCESS.getCode().equals(responseDto.retCode())) {
                log.info("По запросу {} врач {} был успешно зарезервирован на время {} - {}", processDto.processId(),
                        processDto.startParams().doctorId(), processDto.startParams().start(), processDto.startParams().end());
                handleDoctorRequested(responseDto);
            } else if (DoctorRequestRetCodes.BUSY.getCode().equals(responseDto.retCode())) {
                log.warn("По запросу {} не удалось зарезервировать врача {} на время {} - {}, так как врач в это время занят", processDto.processId(),
                        processDto.startParams().doctorId(), processDto.startParams().start(), processDto.startParams().end());
                handleRollbackProcess(processDto);
            } else if (DoctorRequestRetCodes.EXCEPTION.getCode().equals(responseDto.retCode())) {
                log.warn("По запросу {} не удалось резервирование врача {} на время {} - {} завершилось с ошибкой",
                        processDto.processId(), processDto.startParams().doctorId(), processDto.startParams().start(),
                        processDto.startParams().end());
                handleRollbackProcess(processDto);
            }
        } catch (ProcessNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Произошла ошибка при обработке ответа запроса {}", responseDto.processId(), e);
            if (processDto != null) {
                handleRollbackProcess(processDto);
            }
            throw e;
        }
    }

    /**
     * Обработка события отмены шага назначения врача
     * @param processDto Выполняемый процесс
     */
    private void handleRollbackProcess(ProcessDto processDto) {
        ProcessDto process = new ProcessDto(processDto.processId(), processDto.startParams(), ProcessExecutionState.ROLLING_BACK,
                StepState.ROLLING_BACK, // Предыдущий шаг - создание назначения
                StepState.CANCELLED, // Отмена шага резервинование врача
                StepState.CANCELLED, StepState.CANCELLED // Шаги, которые ещё не выполнялись помечаем отмененные
        );
        processService.update(process);
    }

    private void handleDoctorRequested(DoctorRequestResponseDto responseDto) {
        Action act = Action.valueOf(responseDto.action());
        switch (act) {
            case Action.ARRANGE:

                break;
            case Action.CANCEL:
                break;
            default:
        }
    }
}
