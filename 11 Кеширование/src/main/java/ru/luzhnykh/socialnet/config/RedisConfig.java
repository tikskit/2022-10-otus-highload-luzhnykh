package ru.luzhnykh.socialnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ru.luzhnykh.socialnet.dto.PostDto;

import java.util.List;

/**
 * Конфигурация для Redis
 */
@Configuration
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        rsc.setHostName("localhost");
        rsc.setPort(6379);

        LettuceConnectionFactory lcf = new LettuceConnectionFactory(rsc);
        lcf.afterPropertiesSet();
        return lcf;
    }

    @Bean
    public RedisTemplate<String, List<PostDto>> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, List<PostDto>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
