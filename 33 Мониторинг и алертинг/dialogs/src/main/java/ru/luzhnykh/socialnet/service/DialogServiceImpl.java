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
     * @param userId ИД пользователя
     * @param text   Текст диалога
     */
    @Override
    public void add(String userId, String text) {
        dialogDao.add(new DialogDto(userId, text));
    }
}
