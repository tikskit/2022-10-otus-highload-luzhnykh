package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.TokenDao;

/**
 * Реализация сервиса токенов. Выдает и валидирует токены
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenDao tokenDao;


    /**
     * Является ли токен валидным
     *
     * @param token Токен для проверки
     * @return true если токен валидный, иначе false
     */
    @Override
    public boolean validate(String token) {
        return StringUtils.isNotBlank(token) && tokenDao.get(token).isPresent();
    }
}
