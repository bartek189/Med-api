package pl.kurs.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.annotation.Pesel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdatePatientCommand {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Pesel
    private String pesel;
    @Email
    private String email;
}
