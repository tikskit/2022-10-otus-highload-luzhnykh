package ru.luzhnykh.distribtx.resources.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.resources.dao.DoctorRequestDao;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestResponseDto;
import ru.luzhnykh.distribtx.resources.enums.DoctorRequestRetCodes;
import ru.luzhnykh.distribtx.resources.events.DoctorRequestReplyPublisher;

/**
 * Реализация сервиса резервирования врачей
 */
@Service
@RequiredArgsConstructor
public class DoctorRequestServiceImpl implements DoctorRequestService {

    private final DoctorRequestDao doctorRequestDao;
    private final DoctorRequestReplyPublisher publisher;

    @Override
    public void addRequest(@NonNull DoctorRequestDto requestDto) {
        DoctorRequestResponseDto responseDto;

        try {
            if (doctorRequestDao.activeExist(requestDto)) {
                responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.BUSY.getCode(), "");
            } else {
                doctorRequestDao.addRequest(requestDto);
                responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.SUCCESS.getCode(), "");
            }
        } catch (DataAccessException e) {
            responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.EXCEPTION.getCode(), e.getMessage());
        }

        publisher.publish(responseDto);
    }
}
