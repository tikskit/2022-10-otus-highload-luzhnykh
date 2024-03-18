package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.DialogDto;
import ru.luzhnykh.socialnet.dto.DialogMessageDto;

import java.util.List;

/**
 * Dao диалог
 */
public interface DialogDao {
    /**
     * Добавить диалог
     */
    void add(DialogDto dialog);

    /**
     * Получить все сообщения, отправленные пользователем senderId для пользователя senderId и упорядоченные по времени отправки
     */
    List<DialogMessageDto> getMessage(String senderId, String receiverId);
}
