package pl.kurs.mapper;


import org.mapstruct.Mapper;
import pl.kurs.model.dto.VisitDto;
import pl.kurs.model.entity.Visit;

@Mapper(componentModel = "spring")

public interface VisitToVisitDtoMapper {
    VisitDto map(Visit visit);


}
