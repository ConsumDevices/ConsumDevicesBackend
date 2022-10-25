package ro.tuc.ds2020.dtos.validators.annotation;

import ro.tuc.ds2020.dtos.validators.AgeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AgeValidator.class})

//interfata, pe care o sa o folosim ca adnotare
public @interface AgeLimit {

    int limit() default 120; //default la limita 120

    //mesajul care sa fie dat in caz ca limita nu se respecta
    String message() default "Age  does not match the required adult limit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
