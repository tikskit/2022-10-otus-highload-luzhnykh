package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.service.TokenService;

/**
 * Контроллер сущности Друг
 */
@RestController
@RequiredArgsConstructor
public class FriendController {
    private final TokenService tokenService;

    /**
     * Добавить в друзья
     *
     * @param actorUserId ИД пользователя, совершающего действие
     * @param user_id     ИД пользователя, которого добавляют в друзья
     * @param token       Токен
     */
    @PutMapping("/friend/set/{user_id}")
    public ResponseEntity<String> set(@PathVariable("user_id") String actorUserId, @RequestParam String user_id,
                                      @RequestHeader String token) {
        if (tokenService.validate(token)) {
            return null;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Отфрендить
     *
     * @param actorUserId ИД пользователя, совершающего действие
     * @param user_id     Френд пользователя
     * @param token       Токен
     */
    @PutMapping("/friend/delete/{user_id}")
    public ResponseEntity<String> delete(@PathVariable("user_id") String actorUserId, @RequestParam String user_id,
                                         @RequestHeader String token) {
        if (tokenService.validate(token)) {
            return null;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
