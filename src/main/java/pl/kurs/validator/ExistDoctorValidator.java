package pl.kurs.validator;

import lombok.AllArgsConstructor;
import pl.kurs.annotation.ExistDoctor;
import pl.kurs.repository.DoctorRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class ExistDoctorValidator implements ConstraintValidator<ExistDoctor, Long> {
    private final DoctorRepository doctorRepository;

    @Override
    public void initialize(ExistDoctor constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return doctorRepository.existsById(aLong);
    }
}
