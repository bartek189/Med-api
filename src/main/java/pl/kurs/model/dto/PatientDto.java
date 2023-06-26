package pl.kurs.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PatientDto {
    private long id;
    private String name;
    private String surname;
    private String pesel;
    private String email;
    private Boolean deleted;

}
