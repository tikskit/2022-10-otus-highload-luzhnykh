package ru.luzhnykh.socialnet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.luzhnykh.socialnet.dto.CreatePostDto;
import ru.luzhnykh.socialnet.dto.CreatePostResDto;
import ru.luzhnykh.socialnet.dto.PostDto;
import ru.luzhnykh.socialnet.dto.UpdatePostDto;
import ru.luzhnykh.socialnet.exceptions.PostNotFoundException;
import ru.luzhnykh.socialnet.service.PostService;
import ru.luzhnykh.socialnet.service.TokenService;

/**
 * Контроллер сущности Пост
 */
@RestController
@RequiredArgsConstructor
public class PostController {
    private final TokenService tokenService;
    private final PostService postService;
    @PostMapping("/post/create")
    public ResponseEntity<CreatePostResDto> create(@RequestBody CreatePostDto createPostDto,
                                                   @RequestHeader String token) {
        if (tokenService.validate(token)) {
            return ResponseEntity.ok(postService.add(createPostDto));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/post/update")
    public ResponseEntity<String> update(@RequestBody UpdatePostDto updatePostDto,
                                         @RequestHeader String token) {
        if (tokenService.validate(token)) {
            postService.update(updatePostDto);
            return ResponseEntity.ok("Успешно изменен пост");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/post/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id, @RequestHeader String token) {
        if (tokenService.validate(token)) {
            postService.delete(id);
            return ResponseEntity.ok("Успешно удален пост");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/post/get/{id}")
    public ResponseEntity<PostDto> get(@PathVariable String id, @RequestHeader String token) {
        if (tokenService.validate(token)) {
            return postService.get(id)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new PostNotFoundException(String.format("Пост '%s' не найден", id)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
