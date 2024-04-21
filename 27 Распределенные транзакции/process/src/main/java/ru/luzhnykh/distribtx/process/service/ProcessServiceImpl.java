package ru.luzhnykh.distribtx.process.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.process.dao.ProcessDao;
import ru.luzhnykh.distribtx.process.domain.Process;
import ru.luzhnykh.distribtx.process.dto.ProcessDto;
import ru.luzhnykh.distribtx.process.enums.ProcessState;
import ru.luzhnykh.distribtx.process.enums.StepState;
import ru.luzhnykh.distribtx.process.events.Publisher;
import ru.luzhnykh.distribtx.process.exceptions.RequestNotFoundException;
import ru.luzhnykh.distribtx.process.mapper.ProcessMapper;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.enums.Action;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProcessServiceImpl implements ProcessService {

    private final ProcessDao processDao;
    private final ProcessMapper mapper;
    private final Publisher publisher;

    private void move(ProcessDto process) {
        if (ProcessState.EXECUTING.equals(process.state())) {
            if (StepState.PLANNED.equals(process.prescriptionStep())) {
                doPrescriptionStep(process);
            } else if (StepState.PLANNED.equals(process.doctorStep())) {

            } else if (StepState.PLANNED.equals(process.paymentStep())) {

            } else {
                // notification step
            }
        } else if (ProcessState.ROLLING_BACK.equals(process.state())) {
            if (StepState.DONE.equals(process.doctorStep())) {

            } else if (StepState.DONE.equals(process.prescriptionStep())) {

            } else {
                // Остальные шаги не откатываются
            }
        }
    }

    private void doPrescriptionStep(ProcessDto process) {
        Process domain = new Process(process.requestId(), ProcessState.EXECUTING.name(), StepState.EXECUTING.name(),
                process.doctorStep().name(), process.paymentStep().name(), process.notificationStep().name());
        processDao.update(domain);
        DoctorRequestDto request = new DoctorRequestDto(domain.requestId(), "doctor-id-2", "resc-id-2",
                LocalDate.of(2024, Month.APRIL, 1), LocalDate.of(2024, Month.APRIL, 2),
                Action.ARRANGE.name());
        publisher.publishDoctorRequest(request);
    }

    @Override
    public void startProcess() {
        ProcessDto dto = new ProcessDto(UUID.randomUUID().toString(), ProcessState.EXECUTING, StepState.PLANNED,
                StepState.PLANNED, StepState.PLANNED, StepState.PLANNED);
        move(dto);
    }

    @Override
    public void move(String requestId) {
        Process process = processDao.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(String.format("Запрос %s не найден", requestId)));
        move(mapper.toDto(process));
    }
}
