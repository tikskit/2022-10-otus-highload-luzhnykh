package ru.luzhnykh.distribtx.resources.mapper;

import org.mapstruct.Mapper;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.resources.domain.DoctorRequest;

@Mapper(componentModel = "spring")
public interface DoctorRequestMapper {
    DoctorRequest toDomain(DoctorRequestDto dto);
}
