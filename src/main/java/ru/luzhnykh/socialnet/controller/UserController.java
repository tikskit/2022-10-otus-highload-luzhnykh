package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.luzhnykh.socialnet.domain.User;
import ru.luzhnykh.socialnet.exceptions.IllegalUserIdException;
import ru.luzhnykh.socialnet.exceptions.UserNotFoundException;
import ru.luzhnykh.socialnet.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/get/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id).orElseThrow(() -> new UserNotFoundException(String.format("Пользователь %s не найден", id)));
    }

    @ExceptionHandler({UserNotFoundException.class, IllegalUserIdException.class})
    public ResponseEntity<String> handleIllegalUserId(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
