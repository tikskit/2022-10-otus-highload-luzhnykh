package ru.luzhnykh.distribtx.process.dao;

import ru.luzhnykh.distribtx.process.domain.Process;

import java.util.Optional;

public interface ProcessDao {
    Optional<Process> findById(String processId);
    void update(Process process);
}
