package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.FeedCacheDao;
import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.List;
import java.util.Optional;

/**
 * Реализация кэша ленты друзей пользователя с использованием Redis.
 * Отвечает за инвалидацию данных кэша
 */
@Service
@RequiredArgsConstructor
public class FeedCacheServiceRedis implements FeedCacheService {

    private final FeedCacheDao feedCacheDao;

    /**
     * Получть ленту пользователя из кэша
     *
     * @param userId ИД пользователя, для которого получаем ленту
     * @return Лента пользователя, либо empty, если кэш пуст
     */
    @Override
    public Optional<List<PostDto>> get(String userId) {
        List<PostDto> feed = feedCacheDao.get(userId);
        return feed != null ? Optional.of(feed) : Optional.empty();
    }

    /**
     * Поместить ленту друзей пользователя в кэш
     *
     * @param userId ИД пользователя
     * @param feed   Лента друзей пользователя
     */
    @Override
    public void put(String userId, List<PostDto> feed) {
        feedCacheDao.set(userId, feed);
    }
}
