package pl.kurs.mapper;

import org.mapstruct.Mapper;
import pl.kurs.model.dto.DoctorDto;
import pl.kurs.model.entity.Doctor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorToDoctorDtoMapper {
    DoctorDto map(Doctor doctor);

    List<DoctorDto> mapList(List<Doctor> doctor);
}
