package pl.kurs.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.annotation.Pesel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientCommand {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    @Pesel
    private String pesel;

    @Email
    private String email;


}
