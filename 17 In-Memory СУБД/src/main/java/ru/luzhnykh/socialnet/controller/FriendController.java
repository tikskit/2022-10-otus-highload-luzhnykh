package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.service.FriendService;
import ru.luzhnykh.socialnet.service.TokenService;

/**
 * Контроллер сущности Друг
 */
@RestController
@RequiredArgsConstructor
public class FriendController {
    private final TokenService tokenService;
    private final FriendService friendService;

    /**
     * Добавить в друзья
     *
     * @param actorUserId ИД пользователя, совершающего действие
     * @param userId     ИД пользователя, которого добавляют в друзья
     * @param token       Токен
     */
    @PutMapping("/friend/set/{user_id}")
    public ResponseEntity<String> set(@PathVariable("user_id") String actorUserId, @RequestParam("user_id") String userId,
                                      @RequestHeader String token) {
        if (tokenService.validate(token)) {
            friendService.addFriend(actorUserId, userId);
            return ResponseEntity.ok("Пользователь успешно указал своего друга");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Отфрендить
     *
     * @param actorUserId ИД пользователя, совершающего действие
     * @param userId     Френд пользователя
     * @param token       Токен
     */
    @PutMapping("/friend/delete/{user_id}")
    public ResponseEntity<String> delete(@PathVariable("user_id") String actorUserId, @RequestParam("user_id") String userId,
                                         @RequestHeader String token) {
        if (tokenService.validate(token)) {
            friendService.delFriend(actorUserId, userId);
            return ResponseEntity.ok("Пользователь успешно удалил из друзей пользователя");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
