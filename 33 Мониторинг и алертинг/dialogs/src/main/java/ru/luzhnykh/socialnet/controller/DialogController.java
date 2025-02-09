package ru.luzhnykh.socialnet.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.dto.DialogRequestDto;
import ru.luzhnykh.socialnet.service.DialogService;
import ru.luzhnykh.socialnet.service.EteLogId;
import ru.luzhnykh.socialnet.service.TokenService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DialogController {
    private static final String LOG_ID_HEADER = "log_id";
    private final TokenService tokenService;
    private final DialogService dialogService;
    private final EteLogId eteLogId;

    @Timed("dialog_send_api")
    @PostMapping("/dialog/{user_id}/send")
    public ResponseEntity<String> send(@PathVariable String user_id, @RequestBody DialogRequestDto text,
                                       @RequestHeader(value = LOG_ID_HEADER, required = false) String logId,
                                       @RequestHeader String token) {
        eteLogId.setLogId(logId);

        if (tokenService.validate(token)) {
            dialogService.add(user_id, text.text());
            logId = logId == null? eteLogId.getLogId() : logId;
            log.info("Track ID: {} - Добавлен диалог от пользователя {}", logId, user_id);
            return ResponseEntity.ok("ok");
        } else {
            log.warn("Track ID: {} - Пользователь не авторизован", logId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
