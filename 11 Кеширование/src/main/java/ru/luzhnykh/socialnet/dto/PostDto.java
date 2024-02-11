package ru.luzhnykh.socialnet.dto;

/**
 * Dto сущности Post
 *
 * @param id             ИД поста
 * @param text           тест поста
 * @param author_user_id Ид автора поста
 */
public record PostDto(String id, String text, String author_user_id) {
}
