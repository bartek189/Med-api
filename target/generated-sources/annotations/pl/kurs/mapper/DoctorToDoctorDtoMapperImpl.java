package pl.kurs.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kurs.model.dto.DoctorDto;
import pl.kurs.model.entity.Doctor;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-19T23:55:07+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Azul Systems, Inc.)"
)
@Component
public class DoctorToDoctorDtoMapperImpl implements DoctorToDoctorDtoMapper {

    @Override
    public DoctorDto map(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId( doctor.getId() );
        doctorDto.setName( doctor.getName() );
        doctorDto.setSurname( doctor.getSurname() );
        doctorDto.setNip( doctor.getNip() );
        doctorDto.setDeleted( doctor.getDeleted() );

        return doctorDto;
    }

    @Override
    public List<DoctorDto> mapList(List<Doctor> doctor) {
        if ( doctor == null ) {
            return null;
        }

        List<DoctorDto> list = new ArrayList<DoctorDto>( doctor.size() );
        for ( Doctor doctor1 : doctor ) {
            list.add( map( doctor1 ) );
        }

        return list;
    }
}
