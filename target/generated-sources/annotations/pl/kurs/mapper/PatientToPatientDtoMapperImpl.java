package pl.kurs.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kurs.model.dto.PatientDto;
import pl.kurs.model.entity.Patient;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-19T23:55:07+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Azul Systems, Inc.)"
)
@Component
public class PatientToPatientDtoMapperImpl implements PatientToPatientDtoMapper {

    @Override
    public PatientDto map(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientDto patientDto = new PatientDto();

        patientDto.setId( patient.getId() );
        patientDto.setName( patient.getName() );
        patientDto.setSurname( patient.getSurname() );
        patientDto.setPesel( patient.getPesel() );
        patientDto.setEmail( patient.getEmail() );
        patientDto.setDeleted( patient.getDeleted() );

        return patientDto;
    }

    @Override
    public List<PatientDto> mapList(List<Patient> patients) {
        if ( patients == null ) {
            return null;
        }

        List<PatientDto> list = new ArrayList<PatientDto>( patients.size() );
        for ( Patient patient : patients ) {
            list.add( map( patient ) );
        }

        return list;
    }
}
