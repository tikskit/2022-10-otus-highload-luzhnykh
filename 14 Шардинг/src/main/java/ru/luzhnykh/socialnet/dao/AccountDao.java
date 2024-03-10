package ru.luzhnykh.socialnet.dao;

import ru.luzhnykh.socialnet.dto.AccountDto;

/**
 * Dao аккаунт пользователя
 */
public interface AccountDao {
    /**
     * Добавить аккаунт пользователя
     *
     * @param account аккаунт
     */
    void addAccount(AccountDto account);

    /**
     * Существует ли пара логин-пароль
     *
     * @param account аккаунт
     */
    boolean match(AccountDto account);
}
