package adventofcode;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeightValidator implements ConstraintValidator<Height,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final int height = Integer.parseInt(value.substring(0, value.length() - 2));
        if(value.endsWith("cm")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be between 150cm and 193cm").addConstraintViolation();
            return height >= 150 && height <= 193;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("must be between 59in and 76in").addConstraintViolation();
            return height >= 59 && height <= 76;
        }
    }
}
