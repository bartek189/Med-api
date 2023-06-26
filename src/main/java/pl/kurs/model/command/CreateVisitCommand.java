package pl.kurs.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.annotation.ExistDoctor;
import pl.kurs.annotation.ExistPatient;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVisitCommand {


    @Positive
    @ExistDoctor
    private long doctorId;
    @Positive
    @ExistPatient
    private long patientId;
    @FutureOrPresent
    private LocalDateTime date;
    @Positive
    private int lengthInMinutes;
}
