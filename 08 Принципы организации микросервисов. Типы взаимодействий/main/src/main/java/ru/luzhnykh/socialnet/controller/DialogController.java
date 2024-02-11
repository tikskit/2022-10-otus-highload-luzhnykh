package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.config.DialogsHttpClient;
import ru.luzhnykh.socialnet.dto.DialogRequestDto;
import ru.luzhnykh.socialnet.service.EteLogId;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DialogController {
    private static final String LOG_ID_HEADER = "log_id";
    private final EteLogId eteLogId;
    private final DialogsHttpClient dialogsHttpClient;

    @PostMapping("/dialog/{user_id}/send")
    public ResponseEntity<String> send(@PathVariable String user_id, @RequestBody DialogRequestDto text,
                       @RequestHeader(value = LOG_ID_HEADER, required = false) String logId,
                       @RequestHeader String token) {
        logId = logId == null? eteLogId.getLogId() : logId;
        log.info("Log trace id: {}", logId);
        try {
            return dialogsHttpClient.send(user_id, text, logId, token);
        } catch (Exception e) {
            log.info("Log trace id: {}, call status: {}", logId, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
