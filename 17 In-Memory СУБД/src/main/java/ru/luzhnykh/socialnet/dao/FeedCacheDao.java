package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.List;

/**
 * Дао для сущности лента друзей в кэше
 */
public interface FeedCacheDao {

    /**
     * Получить ленту друзей пользователя
     *
     * @param userId ИД пользователя
     * @return Лента друзей
     */
    List<PostDto> get(String userId);

    /**
     * Запомнить ленту другей пользователя
     *
     * @param userId ИД пользователя
     * @param feed   Лента друзей
     */
    void set(String userId, List<PostDto> feed);
}
