package pl.kurs.mapper;

import org.mapstruct.Mapper;
import pl.kurs.model.dto.PatientDto;
import pl.kurs.model.entity.Patient;

import java.util.List;

@Mapper(componentModel = "spring")

public interface PatientToPatientDtoMapper {
    PatientDto map(Patient patient);

    List<PatientDto> mapList(List<Patient> patients);
}
