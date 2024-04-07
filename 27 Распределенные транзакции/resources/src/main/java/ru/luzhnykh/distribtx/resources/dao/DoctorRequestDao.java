package ru.luzhnykh.distribtx.resources.dao;

import org.springframework.dao.DataAccessException;
import ru.luzhnykh.distribtx.resources.domain.DoctorRequest;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;

import java.util.Optional;

/**
 * Dao для работы с заявками на врачей
 */
public interface DoctorRequestDao {

    /**
     * Проверяет, сущестсвуют ли активные заявки на врача
     * @param requestDto Заявка
     */
    boolean activeExist(DoctorRequestDto requestDto);

    /**
     * Добавить заявку на врача
     * @param requestDto
     */
    void addRequest(DoctorRequestDto requestDto) throws DataAccessException;
}
