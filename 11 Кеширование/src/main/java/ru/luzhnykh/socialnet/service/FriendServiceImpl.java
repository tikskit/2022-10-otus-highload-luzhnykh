package ru.luzhnykh.socialnet.service;

import org.springframework.stereotype.Service;

/**
 * Реализация сущности Друг
 */
@Service
public class FriendServiceImpl implements FriendService {

    /**
     * Добавить друга
     *
     * @param userId       ИД пользователя, для которого добавляем друга
     * @param friendUserId Ид пользователя-друга
     */
    @Override
    public void addFriend(String userId, String friendUserId) {

    }

    /**
     * Отфрендить друга
     *
     * @param userId       ИД пользователя, для которого удаляем друга
     * @param friendUserId Ид пользователя-друга
     */
    @Override
    public void delFriend(String userId, String friendUserId) {

    }
}
