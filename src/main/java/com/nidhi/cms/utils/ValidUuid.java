
package com.nidhi.cms.utils;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * Validate that the annotated string is not {@code null} or empty and contains invalid characters .
 *
 * @author Ankur Bansala
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UuidValidator.class)
public @interface ValidUuid {

    String message() default "{javax.validation.constraints.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean isMandatory() default true;

}
