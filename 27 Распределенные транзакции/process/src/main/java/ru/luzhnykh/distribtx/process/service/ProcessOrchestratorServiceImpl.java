package ru.luzhnykh.distribtx.process.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.common.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.common.enums.Action;
import ru.luzhnykh.distribtx.process.dao.ProcessDao;
import ru.luzhnykh.distribtx.process.domain.Process;
import ru.luzhnykh.distribtx.process.dto.ProcessDto;
import ru.luzhnykh.distribtx.process.dto.StartProcessParamDto;
import ru.luzhnykh.distribtx.process.enums.ProcessExecutionState;
import ru.luzhnykh.distribtx.process.enums.StepState;
import ru.luzhnykh.distribtx.process.events.Publisher;
import ru.luzhnykh.distribtx.process.exceptions.ProcessNotFoundException;

import java.util.UUID;

/**
 * Сервис, который выполняет оркестрацию процесса
 */
@RequiredArgsConstructor
@Service
public class ProcessOrchestratorServiceImpl implements ProcessOrchestratorService {

    private final ProcessDao processDao;
    private final ProcessService processService;
    private final Publisher publisher;
    private final ObjectMapper objectMapper;

    /**
     * Продвижение процесса по состояниям.
     */
    private void move(ProcessDto process) {
        if (ProcessExecutionState.EXECUTING.equals(process.state())) {
            if (StepState.PLANNED.equals(process.prescriptionStep())) {
                doPrescriptionStep(process);
            } else if (StepState.PLANNED.equals(process.doctorStep())) {

            } else if (StepState.PLANNED.equals(process.paymentStep())) {

            } else {
                // notification step
            }
        } else if (ProcessExecutionState.ROLLING_BACK.equals(process.state())) {
            if (StepState.DONE.equals(process.doctorStep())) {

            } else if (StepState.DONE.equals(process.prescriptionStep())) {

            } else {
                // Остальные шаги не откатываются
            }
        } else {
            throw new IllegalStateException(String.format("Недопустимое для движения состояние процесса {}: {}",
                    process.processId(), process.state().name()));
        }
    }

    /**
     * Старт шага изменения назначения
     */
    @SneakyThrows
    private void doPrescriptionStep(ProcessDto process) {
        Process domain = new Process(
                process.processId(),
                objectMapper.writeValueAsString(process.startParams()),
                ProcessExecutionState.EXECUTING.name(),
                StepState.EXECUTING.name(),
                process.doctorStep().name(),
                process.paymentStep().name(),
                process.notificationStep().name()
        );
/*        DoctorRequestDto request = new DoctorRequestDto(
                domain.processId(),
                process.startParams().doctorId(),
                process.startParams().prescriptionId(),
                process.startParams().start(),
                process.startParams().end(),
                Action.ARRANGE.name()
        );*/
        processDao.update(domain);
//        publisher.publishDoctorRequest(request);
    }

    /**
     * Старт шага резервирования врача
     */
    @SneakyThrows
    private void doDoctorStep(ProcessDto process) {
        Process domain = new Process(
                process.processId(),
                objectMapper.writeValueAsString(process.startParams()),
                ProcessExecutionState.EXECUTING.name(),
                process.prescriptionStep().name(),
                StepState.EXECUTING.name(),
                process.paymentStep().name(),
                process.notificationStep().name()
        );
        DoctorRequestDto request = new DoctorRequestDto(
                domain.processId(),
                process.startParams().doctorId(),
                process.startParams().prescriptionId(),
                process.startParams().start(),
                process.startParams().end(),
                Action.ARRANGE.name()
        );
        processDao.update(domain);
        publisher.publishDoctorRequest(request);
    }

    /**
     * Старт процесса
     * @param params Параметры, с которыми процесс был запущен
     */
    @Override
    public void startProcess(StartProcessParamDto params) {
        ProcessDto dto = new ProcessDto(UUID.randomUUID().toString(), params, ProcessExecutionState.EXECUTING,
                StepState.PLANNED, StepState.PLANNED, StepState.PLANNED, StepState.PLANNED);
        move(dto);
    }

    /**
     * Продвинуть процесс далее по состоянию
     */
    @Override
    public void move(String processId) {
        ProcessDto processDto = processService.getById(processId)
                .orElseThrow(() -> new ProcessNotFoundException(String.format("Процесс %s не найден", processId)));
        move(processDto);
    }
}
