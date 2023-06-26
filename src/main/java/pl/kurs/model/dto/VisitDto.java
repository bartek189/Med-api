package pl.kurs.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.model.entity.AcceptVisit;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor

public class VisitDto {
    private long id;
    private LocalDateTime date;
    private int lengthInMinutes;
    private AcceptVisit acceptVisit;


}
