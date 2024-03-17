package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.DialogMessageDto;

import java.util.List;

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

    /**
     * Возвращает сообщения диалога между двумя пользователями
     * @param userId1 ИД пользователя 1
     * @param userId2 ИД пользователя 2
     * @return Сообщения диалога
     */
    List<DialogMessageDto> getList(String userId1, String userId2);
}
