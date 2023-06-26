package pl.kurs.validator;

import lombok.AllArgsConstructor;
import pl.kurs.annotation.ExistPatient;
import pl.kurs.repository.PatientRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@AllArgsConstructor
public class ExistPatientValidator implements ConstraintValidator<ExistPatient, Long> {
    private final PatientRepository patientRepository;

    @Override
    public void initialize(ExistPatient constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long l, ConstraintValidatorContext constraintValidatorContext) {

        if (patientRepository.existsById(l)) {
            return true;
        }
        return false;
    }
}
