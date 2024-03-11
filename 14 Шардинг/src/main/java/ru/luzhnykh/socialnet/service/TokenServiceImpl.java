package ru.luzhnykh.socialnet.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.luzhnykh.socialnet.dao.TokenDao;
import ru.luzhnykh.socialnet.dto.TokenDto;
import ru.luzhnykh.socialnet.exceptions.InvalidTokenException;

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
    public String generate(String userId) {
        tokenDao.add(new TokenDto(userId));
        return userId;
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

    /**
     * Извлекает ИД пользователя из токена
     */
    @Override
    public String getUserFromToken(String token) {
        //В нашем упрощенном примере токен это и есть ИД пользователя
        if (StringUtils.isNotBlank(token)) {
            return token;
        } else {
            throw new InvalidTokenException(String.format("Некорректный токен", token));
        }
    }
}
