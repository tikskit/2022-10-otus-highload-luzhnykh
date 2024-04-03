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

import java.util.List;

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
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/post/feed/{userId}")
    public ResponseEntity<List<PostDto>> feed(@PathVariable String userId,
                                              @RequestParam(defaultValue = "0", required = false) Integer offset,
                                              @RequestParam(defaultValue = "10", required = false) Integer limit,
                                              @RequestHeader String token) {
        if (offset < 0) {
            throw new IllegalArgumentException(String.format("Недопустимое значение параметра offset = %s", offset));
        }

        if (limit < 1) {
            throw new IllegalArgumentException(String.format("Недопустимое значение параметра limit = %s", limit));
        }
        if (tokenService.validate(token)) {
            return ResponseEntity.ok(postService.getFeed(userId, offset, limit));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
