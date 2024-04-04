package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.FriendDao;

/**
 * Реализация сущности Друг
 */
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendDao friendDao;

    /**
     * Добавить друга
     *
     * @param userId       ИД пользователя, для которого добавляем друга
     * @param friendUserId Ид пользователя-друга
     */
    @Override
    public void addFriend(String userId, String friendUserId) {
        friendDao.add(userId, friendUserId);
    }

    /**
     * Отфрендить друга
     *
     * @param userId       ИД пользователя, для которого удаляем друга
     * @param friendUserId Ид пользователя-друга
     */
    @Override
    public void delFriend(String userId, String friendUserId) {
        friendDao.delete(userId, friendUserId);
    }
}
