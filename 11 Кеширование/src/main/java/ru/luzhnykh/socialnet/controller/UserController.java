package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.dto.*;
import ru.luzhnykh.socialnet.exceptions.IllegalUserIdException;
import ru.luzhnykh.socialnet.exceptions.UserNotFoundException;
import ru.luzhnykh.socialnet.service.AccountService;
import ru.luzhnykh.socialnet.service.TokenService;
import ru.luzhnykh.socialnet.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AccountService accountService;
    private final TokenService tokenService;

    @GetMapping("/user/get/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id, @RequestHeader String token) {
        if (tokenService.validate(token)) {
            return userService.getUser(id)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь '%s' не найден", id)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ExceptionHandler({UserNotFoundException.class, IllegalUserIdException.class})
    public ResponseEntity<String> handleIllegalUserId(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResultDto> login(@RequestBody LoginDto loginDto) {
        return accountService.login(loginDto).map(token -> ResponseEntity.ok(new LoginResultDto(token)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserRegisterResult> register(@RequestBody UserRegisterDto user, @RequestHeader String token) {
        if (tokenService.validate(token)) {
            return ResponseEntity.ok(new UserRegisterResult(userService.register(user)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/user/search")
    public ResponseEntity<List<UserDto>> search(@RequestParam("first_name") String firstName,
                                                @RequestParam("last_name") String lastName,
                                                @RequestHeader String token) {
        log.debug("/user/search first_name={} last_name = {}", firstName, lastName);
        if (tokenService.validate(token)) {
            return ResponseEntity.ok(userService.search(firstName, lastName));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
