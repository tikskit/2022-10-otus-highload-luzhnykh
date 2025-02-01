package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.TokenDto;

import java.util.Optional;

/**
 * Dao токена
 */
public interface TokenDao {

    /**
     * Получить токен из БД
     */
    Optional<TokenDto> get(String token);
}
