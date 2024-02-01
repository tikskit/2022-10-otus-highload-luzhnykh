package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luzhnykh.socialnet.config.DialogsConfig;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DialogController {
    private final DialogsConfig dialogsConfig;

    @PostMapping("/dialog/{user_id}/send")
    public ResponseEntity send(@PathVariable String user_id) throws URISyntaxException {
        String uri = String.format("%s/dialog/%s/send", dialogsConfig.getAddress(), user_id);
        log.info("Dialogs address: {}", uri);
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(new URI(uri)).build();
    }
}
