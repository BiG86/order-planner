package it.snorcini.dev.orderplanner.validation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Constraint validator to validate multiple patterns.
 */
@NoArgsConstructor
@AllArgsConstructor
public class PatternConstraintValidator extends AbstractBaseConstraintValidator<PatternConstraint>
        implements ConstraintValidator<PatternConstraint, String> {

    /**
     * Enumeration used just to indicate which regex use to validate field.
     */
    @AllArgsConstructor
    public enum PatternConstraintFieldEnum {

        DESCRIPTION(null);

        private String value;

        protected synchronized String getValue() {
            return this.value;
        }
        protected synchronized void setValue(final String value) {
            this.value = value;
        }
    }

    @Value("${validation.regex.description}")
    private String description;

    private PatternConstraintFieldEnum fieldType;

    /**
     * Initialization method to initialize validator.
     *
     * @param patternConstraint constraint related to the validator
     */
    @Override
    public void initialize(final PatternConstraint patternConstraint) {

        PatternConstraintFieldEnum.DESCRIPTION.setValue(description);

        this.fieldType = patternConstraint.fieldType();
    }

    /**
     * Method containing the validation logic.
     *
     * @param stringField object containing the string to be validated
     * @param cxt constraint validator context
     * @return the validation result
     */
    @Override
    public boolean isValid(final String stringField,
                           final ConstraintValidatorContext cxt) {

        return stringField == null
                || stringField.matches(fieldType.getValue());
    }
}

