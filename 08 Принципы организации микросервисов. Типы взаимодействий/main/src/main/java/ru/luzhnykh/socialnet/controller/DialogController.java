package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class DialogController {
    @PostMapping("/dialog/{user_id}/send")
    ResponseEntity send(@PathVariable String user_id) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(new URI(String.format("http://localhost:8081/dialog/%s/send", user_id))).build();
    }
}
