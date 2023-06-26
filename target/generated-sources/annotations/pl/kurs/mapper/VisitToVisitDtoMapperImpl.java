package pl.kurs.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kurs.model.dto.VisitDto;
import pl.kurs.model.entity.Visit;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-19T23:55:07+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Azul Systems, Inc.)"
)
@Component
public class VisitToVisitDtoMapperImpl implements VisitToVisitDtoMapper {

    @Override
    public VisitDto map(Visit visit) {
        if ( visit == null ) {
            return null;
        }

        VisitDto visitDto = new VisitDto();

        visitDto.setId( visit.getId() );
        visitDto.setDate( visit.getDate() );
        visitDto.setLengthInMinutes( visit.getLengthInMinutes() );
        visitDto.setAcceptVisit( visit.getAcceptVisit() );

        return visitDto;
    }
}
