package ru.luzhnykh.distribtx.resources.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.resources.dao.DoctorRequestDao;
import ru.luzhnykh.distribtx.resources.domain.DoctorRequest;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestResponseDto;
import ru.luzhnykh.distribtx.resources.enums.DoctorRequestRetCodes;
import ru.luzhnykh.distribtx.resources.events.DoctorRequestReplyPublisher;
import ru.luzhnykh.distribtx.resources.mapper.DoctorRequestMapper;

/**
 * Реализация сервиса резервирования врачей
 */
@Service
@RequiredArgsConstructor
public class DoctorRequestServiceImpl implements DoctorRequestService {

    private static final Logger log = LoggerFactory.getLogger(DoctorRequestServiceImpl.class);
    private final DoctorRequestDao doctorRequestDao;
    private final DoctorRequestReplyPublisher publisher;
    private final DoctorRequestMapper mapper;

    @Override
    public void addRequest(@NonNull DoctorRequestDto requestDto) {
        DoctorRequestResponseDto responseDto;
        DoctorRequest domain = mapper.toDomain(requestDto);
        try {
            if (doctorRequestDao.activeExist(domain)) {
                responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.BUSY.getCode(),
                        "", requestDto.action());
            } else {
                doctorRequestDao.addRequest(domain);
                responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.SUCCESS.getCode(),
                        "", requestDto.action());
            }
        } catch (Exception e) {
            responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.EXCEPTION.getCode(),
                    e.getMessage(), requestDto.action());
            log.error("Ошибка при добавлении заявки", e);
        }

        publisher.publish(responseDto);
    }

    @Override
    public void cancelRequest(DoctorRequestDto requestDto) {
        DoctorRequestResponseDto responseDto;
        try {
            DoctorRequest domain = mapper.toDomain(requestDto);
            doctorRequestDao.cancelRequest(domain);
            responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.SUCCESS.getCode(),
                    "", requestDto.action());
        } catch (Exception e) {
            responseDto = new DoctorRequestResponseDto(requestDto.requestId(), DoctorRequestRetCodes.EXCEPTION.getCode(),
                    e.getMessage(), requestDto.action());
            log.error("Ошибка при отмене заявки", e);
        }

        publisher.publish(responseDto);
    }
}
