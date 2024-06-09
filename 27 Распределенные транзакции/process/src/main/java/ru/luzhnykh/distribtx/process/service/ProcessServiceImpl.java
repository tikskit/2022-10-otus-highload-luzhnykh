package ru.luzhnykh.distribtx.process.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.distribtx.process.dao.ProcessDao;
import ru.luzhnykh.distribtx.process.dto.ProcessDto;
import ru.luzhnykh.distribtx.process.mapper.ProcessMapper;

import java.util.Optional;

/**
 * Реализация сервиса для работы с процессом
 */
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {
    private final ProcessDao dao;
    private final ProcessMapper mapper;
    /**
     * Получить процесс по ИД
     * @param processId ИД процесса
     */
    @Override
    public Optional<ProcessDto> getById(String processId) {
        return dao.findById(processId).map(mapper::toDto);
    }

    /**
     * Обновить процесс в БД
     */
    @Override
    public void update(ProcessDto process) {
        dao.update(mapper.toDomain(process));
    }
}
