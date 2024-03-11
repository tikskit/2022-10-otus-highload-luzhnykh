package ru.luzhnykh.socialnet.service;

/**
 * Сервис диалогов
 */
public interface DialogService {
    /**
     * Добавить диалог
     *
     * @param senderId   ИД пользователя
     * @param receiverId ИД получателя
     * @param text       Текст диалога
     */
    void add(String senderId, String receiverId, String text);
}
