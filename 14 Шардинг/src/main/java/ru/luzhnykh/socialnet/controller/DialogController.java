package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.dto.DialogMessageDto;
import ru.luzhnykh.socialnet.dto.DialogRequestDto;
import ru.luzhnykh.socialnet.service.DialogService;
import ru.luzhnykh.socialnet.service.TokenService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DialogController {

    private final TokenService tokenService;
    private final DialogService dialogService;

    @PostMapping("/dialog/{user_id}/send")
    public ResponseEntity<String> send(@PathVariable("user_id") String receiverId,
                                       @RequestBody DialogRequestDto text,
                                       @RequestHeader String token) {
        if (tokenService.validate(token)) {
            String senderId = tokenService.getUserFromToken(token);
            dialogService.add(senderId, receiverId, text.text());
            log.info("Добавлено сообщение от пользователя {} для пользователя {}", senderId, receiverId);
            return ResponseEntity.ok("ok");
        } else {
            log.warn("Пользователь не авторизован");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/dialog/{user_id}/list")
    public ResponseEntity<List<DialogMessageDto>> list(@PathVariable("user_id") String receiverId,
                                                       @RequestHeader String token) {
        if (tokenService.validate(token)) {
            String senderId = tokenService.getUserFromToken(token);
            List<DialogMessageDto> list = dialogService.getList(senderId, receiverId);
            return ResponseEntity.ok(list);
        } else {
            log.warn("Пользователь не авторизован");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
