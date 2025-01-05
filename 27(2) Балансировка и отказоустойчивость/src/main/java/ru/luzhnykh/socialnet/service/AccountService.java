package ru.luzhnykh.socialnet.service;

import ru.luzhnykh.socialnet.dto.AccountDto;

/**
 * Сервис сущности Аккаунт
 */
public interface AccountService {

    /**
     * Добавление нового аккакнта
     */
    void addAccount(AccountDto accountDto);
}
