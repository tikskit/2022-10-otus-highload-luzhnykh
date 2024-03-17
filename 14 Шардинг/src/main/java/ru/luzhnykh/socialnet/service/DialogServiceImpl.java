package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.DialogDao;
import ru.luzhnykh.socialnet.dto.DialogDto;
import ru.luzhnykh.socialnet.dto.DialogMessageDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * Возвращает сообщения диалога между двумя пользователями
     *
     * @param userId1 ИД пользователя 1
     * @param userId2 ИД пользователя 2
     * @return Сообщения диалога
     */
    @Override
    public List<DialogMessageDto> getList(String userId1, String userId2) {
        List<DialogMessageDto> list = Stream.concat(
                        dialogDao.getMessage(userId1, userId2).stream(),
                        dialogDao.getMessage(userId2, userId1).stream()
                )
                .sorted(Comparator.comparing(DialogMessageDto::datetime, Comparator.naturalOrder()))
                .collect(Collectors.toList());
        return list;
    }
}
