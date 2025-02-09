package ru.luzhnykh.socialnet.service;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EteLogId {
    private final String logId = UUID.randomUUID().toString();
}
