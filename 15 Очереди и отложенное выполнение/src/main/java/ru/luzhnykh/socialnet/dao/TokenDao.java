package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.TokenDto;

import java.util.Optional;

/**
 * Dao токена
 */
public interface TokenDao {

    /**
     * добавить токен
     */
    void add(TokenDto token);

    /**
     * Получить токен из БД
     */
    Optional<TokenDto> get(String token);
}
