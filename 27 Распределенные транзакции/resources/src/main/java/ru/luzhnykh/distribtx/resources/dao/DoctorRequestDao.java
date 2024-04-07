package ru.luzhnykh.distribtx.resources.dao;

import ru.luzhnykh.distribtx.resources.domain.DoctorRequest;

/**
 * Dao для работы с заявками на врачей
 */
public interface DoctorRequestDao {

    /**
     * Проверяет, сущестсвуют ли активные заявки на врача
     * @param request Заявка
     */
    boolean activeExist(DoctorRequest request);

    /**
     * Добавить заявку на врача
     * @param request
     */
    void addRequest(DoctorRequest request);

    void cancelRequest(DoctorRequest request);
}
