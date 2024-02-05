package ru.luzhnykh.socialnet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер сущности Пост
 */
@RestController
public class PostController {
    @PostMapping("/post/create")
    public ResponseEntity<String> create()
}
