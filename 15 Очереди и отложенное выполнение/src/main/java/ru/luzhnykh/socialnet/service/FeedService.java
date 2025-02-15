package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.List;

/**
 * Сервис получения ленты
 */
public interface FeedService {
    /**
     * Получить ленту для пользователя
     *
     * @param userId ИД пользователя
     * @return Лента постов
     */
    List<PostDto> getFeed(String userId);
}
