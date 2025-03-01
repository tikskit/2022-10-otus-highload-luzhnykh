package ru.luzhnykh.socialnet.service;

import java.util.Set;

/**
 * Сервис сущености Друг
 */
public interface FriendService {

    /**
     * Добавить друга
     *
     * @param userId       ИД пользователя, для которого добавляем друга
     * @param friendUserId Ид пользователя-друга
     */
    void addFriend(String userId, String friendUserId);

    /**
     * Отфрендить друга
     *
     * @param userId       ИД пользователя, для которого удаляем друга
     * @param friendUserId Ид пользователя-друга
     */
    void delFriend(String userId, String friendUserId);

    Set<String> getFriendsOf(String userId);
}
