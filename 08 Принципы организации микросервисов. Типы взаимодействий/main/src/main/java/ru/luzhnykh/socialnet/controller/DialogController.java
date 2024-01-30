package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.service.TokenService;

@RestController
@RequiredArgsConstructor
public class DialogController {
    private final TokenService tokenService;

    @PostMapping("/dialog/{user_id}/send")
    public ResponseEntity<String> send(@PathVariable String user_id, @RequestBody String text, @RequestHeader String token) {
        if (tokenService.validate(token)) {
//            return ResponseEntity.ok(userService.search(firstName, lastName));
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
