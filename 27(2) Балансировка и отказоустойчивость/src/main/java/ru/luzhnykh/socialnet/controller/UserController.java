package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.dto.UserDto;
import ru.luzhnykh.socialnet.dto.UserRegisterDto;
import ru.luzhnykh.socialnet.dto.UserRegisterResult;
import ru.luzhnykh.socialnet.exceptions.IllegalUserIdException;
import ru.luzhnykh.socialnet.exceptions.UserNotFoundException;
import ru.luzhnykh.socialnet.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user/get/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        log.info("/user/get/{}", id);
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь '%s' не найден", id)));
    }

    @ExceptionHandler({UserNotFoundException.class, IllegalUserIdException.class})
    public ResponseEntity<String> handleIllegalUserId(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserRegisterResult> register(@RequestBody UserRegisterDto user) {
        return ResponseEntity.ok(new UserRegisterResult(userService.register(user)));
    }

    @GetMapping("/user/search")
    public ResponseEntity<List<UserDto>> search(@RequestParam("first_name") String firstName,
                                                @RequestParam("last_name") String lastName) {
        log.info("/user/search first_name={} last_name = {}", firstName, lastName);
        return ResponseEntity.ok(userService.search(firstName, lastName));
    }
}
