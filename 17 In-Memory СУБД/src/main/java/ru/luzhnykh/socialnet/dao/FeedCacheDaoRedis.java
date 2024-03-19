package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.PostDto;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedCacheDaoRedis implements FeedCacheDao {

    /**
     * Время жизни ленты в кэше
     */
    private final static Duration FEED_DURATION = Duration.of(1L, ChronoUnit.MINUTES);

    private final RedisOperations<String, List<PostDto>> ops;

    @Override
    public List<PostDto> get(String userId) {
        return ops.opsForValue().get(userId);
    }

    @Override
    public void set(String userId, List<PostDto> feed) {
        ops.opsForValue().set(userId, feed, FEED_DURATION);
    }
}
