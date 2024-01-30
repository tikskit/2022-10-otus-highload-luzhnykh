package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.TokenDto;

/**
 * Dao токена
 */
public interface TokenDao {

    /**
     * добавить токен
     */
    void add(TokenDto token);
}
