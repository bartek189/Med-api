package pl.kurs.annotation;

import pl.kurs.validator.PeselValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeselValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pesel {
    String message() default "wrong Pesel";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
