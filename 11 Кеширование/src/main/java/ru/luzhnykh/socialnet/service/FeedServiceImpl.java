package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.PostDao;
import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.List;

/**
 * Сервис получения ленты
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FeedServiceImpl implements FeedService {

    // Смещение в ленте по умолчанию
    private static final Integer FEED_OFFSET = 0;
    // Количество постов в ленте по умолчанию
    private static final Integer FEED_SIZE = 1000;
    private final FeedCacheService feedCacheService;
    private final PostDao postDao;

    /**
     * Получить ленту для пользователя
     *
     * @param userId ИД пользователя
     * @return Лента постов
     */
    @Override
    public List<PostDto> getFeed(String userId) {
        return feedCacheService.get(userId)
                .map(
                        f -> {
                            log.debug("Попадание в кэш: {}", userId);
                            return f;
                        }
                )
                .orElseGet(
                        () -> {
                            log.debug("Мимо кэша: {}", userId);
                            List<PostDto> feed = postDao.feed(userId, FEED_OFFSET, FEED_SIZE);
                            /* Если feed ничего  не вернул в БД, то всё равно помещаем в кэш, чтобы не ддосили по промахам */
                            feedCacheService.put(userId, feed);
                            return feed;
                        }
                );
    }
}
