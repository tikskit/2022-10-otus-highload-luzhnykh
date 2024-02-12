package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.DialogDto;

/**
 * Dao диалог
 */
public interface DialogDao {
    /**
     * Добавить диалог
     */
    void add(DialogDto dialog);
}
