package ru.luzhnykh.socialnet.service;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EteLogId {
    private String logId = UUID.randomUUID().toString();

    public void setLogId(String logId) {
        if (logId != null) {
            this.logId = logId;
        }
    }
}
