package ru.luzhnykh.socialnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import ru.luzhnykh.socialnet.service.EteLogId;

/**
 * Конфигурация для сквозного логирования
 */
@Configuration
public class ETELogConfig {
    @Bean
    @RequestScope
    public EteLogId eteLogId() {
        return new EteLogId();
    }
}
