package br.com.zup.mercadolivre.validation.annotation;

import br.com.zup.mercadolivre.validation.constraints.UniqueElementsConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueElementsConstraint.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueElements {
    String message() default "{messages.UniqueElements}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
