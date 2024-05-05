package ru.luzhnykh.distribtx.common.mapper;

import org.mapstruct.Mapper;
import ru.luzhnykh.distribtx.common.dto.DoctorRequestDto;
import ru.luzhnykh.distribtx.common.domain.DoctorRequest;

@Mapper(componentModel = "spring")
public interface DoctorRequestMapper {
    DoctorRequest toDomain(DoctorRequestDto dto);
}
