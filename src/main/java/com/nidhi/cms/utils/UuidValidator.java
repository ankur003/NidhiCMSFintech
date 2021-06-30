
package com.nidhi.cms.utils;

import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class UuidValidator implements ConstraintValidator<ValidUuid, String> {

	/** The is mandatory. */
	private boolean isMandatory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(final ValidUuid constraintAnnotation) {
		isMandatory = constraintAnnotation.isMandatory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (StringUtils.isNotBlank(value)) {
			try {
				UUID.fromString(value);
			} catch (final Exception exception) {
				return false;
			}
		} else if (isMandatory) {
			return false;
		}
		// VALID FOR NOT MANDATORY
		return true;
	}

}
