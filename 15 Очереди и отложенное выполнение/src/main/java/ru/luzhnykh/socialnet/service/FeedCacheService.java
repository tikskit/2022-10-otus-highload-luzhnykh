package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.List;
import java.util.Optional;

/**
 * Кэш ленты друзей пользователя
 */
public interface FeedCacheService {

    /**
     * Получть ленту пользователя из кэша
     *
     * @param userId ИД пользователя, для которого получаем ленту
     * @return Лента пользователя, либо empty, если кэш пуст
     */
    Optional<List<PostDto>> get(String userId);

    /**
     * Поместить ленту друзей пользователя в кэш
     *
     * @param userId ИД пользователя
     * @param feed   Лента друзей пользователя
     */
    void put(String userId, List<PostDto> feed);
}
