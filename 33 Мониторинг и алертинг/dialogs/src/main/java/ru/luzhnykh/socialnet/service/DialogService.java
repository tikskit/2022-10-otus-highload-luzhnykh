package ru.luzhnykh.socialnet.service;

/**
 * Сервис диалогов
 */
public interface DialogService {
    /**
     * Добавить диалог
     *
     * @param userId ИД пользователя
     * @param text   Текст диалога
     */
    void add(String userId, String text);
}
