package ru.luzhnykh.distribtx.resources.mapper;

import org.mapstruct.Mapper;
import ru.luzhnykh.distribtx.resources.domain.DoctorRequest;
import ru.luzhnykh.distribtx.resources.dto.DoctorRequestDto;

@Mapper(componentModel = "spring")
public interface DoctorRequestMapper {
    DoctorRequest toDomain(DoctorRequestDto dto);
}
