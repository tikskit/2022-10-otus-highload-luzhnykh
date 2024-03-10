package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.dto.DialogRequestDto;
import ru.luzhnykh.socialnet.service.DialogService;
import ru.luzhnykh.socialnet.service.TokenService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DialogController {

    private final TokenService tokenService;
    private final DialogService dialogService;

    @PostMapping("/dialog/{user_id}/send")
    public ResponseEntity<String> send(@PathVariable String user_id, @RequestBody DialogRequestDto text,
                                       @RequestHeader String token) {
        if (tokenService.validate(token)) {
            dialogService.add(user_id, text.text());
            log.info("Добавлен диалог от пользователя {}", user_id);
            return ResponseEntity.ok("ok");
        } else {
            log.warn("Пользователь не авторизован");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
