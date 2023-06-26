package pl.kurs.validator;

import pl.kurs.annotation.Pesel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PeselValidator implements ConstraintValidator<Pesel, String> {
    @Override
    public void initialize(Pesel constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() == 11) {
            for (int i = 0; i < s.length(); i++) {
                Long.parseLong(s);
            }
            return true;
        }


        return false;
    }
}
