package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.DialogDao;
import ru.luzhnykh.socialnet.dto.DialogDto;

/**
 * Сервис диалогов
 */
@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {
    private final DialogDao dialogDao;

    /**
     * Добавить диалог
     *
     * @param senderId   ИД пользователя
     * @param receiverId ИД получателя
     * @param text       Текст диалога
     */
    @Override
    public void add(String senderId, String receiverId, String text) {
        dialogDao.add(new DialogDto(senderId, receiverId, text));
    }
}
