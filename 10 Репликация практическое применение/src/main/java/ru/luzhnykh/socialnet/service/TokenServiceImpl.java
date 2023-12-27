package ru.luzhnykh.socialnet.service;

import org.springframework.stereotype.Controller;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализация сервиса токенов. Выдает и валидирует токены
 */
@Controller
public class TokenServiceImpl implements TokenService {
    private final ConcurrentHashMap<String, String> tokens = new ConcurrentHashMap<>();

    /**
     * Сгенерировать новый токен
     */
    @Override
    public String generate() {
        String token = UUID.randomUUID().toString();
        tokens.put(token, token);
        return token;
    }

    /**
     * Является ли токен валидным
     *
     * @param token Токен для проверки
     * @return true если токен валидный, иначе false
     */
    @Override
    public boolean validate(String token) {
        return tokens.containsKey(token);
    }
}
