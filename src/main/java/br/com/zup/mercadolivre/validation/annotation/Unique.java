package br.com.zup.mercadolivre.validation.annotation;

import br.com.zup.mercadolivre.validation.constraints.UniqueConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueConstraint.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {
    String field();

    String alias() default "";

    Class<?> modelClass();

    String message() default "{messages.Unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
