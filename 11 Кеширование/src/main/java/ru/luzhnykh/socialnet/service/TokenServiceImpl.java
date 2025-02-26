package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.TokenDao;
import ru.luzhnykh.socialnet.dto.TokenDto;

import java.util.UUID;

/**
 * Реализация сервиса токенов. Выдает и валидирует токены
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenDao tokenDao;
    /**
     * Сгенерировать новый токен
     */
    @Override
    public String generate() {
        String token = UUID.randomUUID().toString();
        tokenDao.add(new TokenDto(token));
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
        return StringUtils.isNotBlank(token) && tokenDao.get(token).isPresent();
    }
}
