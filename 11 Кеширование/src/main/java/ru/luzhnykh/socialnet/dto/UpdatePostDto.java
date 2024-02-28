package ru.luzhnykh.socialnet.dto;

/**
 * Dto обновления поста
 *
 * @param id   ИД поста
 * @param text Текст поста
 */
public record UpdatePostDto(String id, String text) {
}
