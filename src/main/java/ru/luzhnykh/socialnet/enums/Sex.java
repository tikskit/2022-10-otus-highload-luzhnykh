package ru.luzhnykh.socialnet.enums;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Пол пользователя
 */
@RequiredArgsConstructor
@Generated
public enum Sex {
    /**
     * Мужской
     */
    MALE(1),

    /**
     * Женский
     */
    FEMALE(2);

    private final int sexId;

    public static Sex getById(int sexId) {
        return Arrays.stream(values())
                .filter(e -> e.sexId == sexId)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("Недопустимый идентификатор пола: %s", sexId))
                );
    }
}
