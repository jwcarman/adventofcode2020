package adventofcode;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = HeightValidator.class)
public @interface Height {
    String message() default "Invalid height";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
