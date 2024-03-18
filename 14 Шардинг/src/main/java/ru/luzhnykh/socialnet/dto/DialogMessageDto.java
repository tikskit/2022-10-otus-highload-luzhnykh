package ru.luzhnykh.socialnet.dto;

import java.time.OffsetDateTime;

/**
 * Сообщение из диалога
 */
public record DialogMessageDto(String from, String to, String text, OffsetDateTime datetime) {
}
