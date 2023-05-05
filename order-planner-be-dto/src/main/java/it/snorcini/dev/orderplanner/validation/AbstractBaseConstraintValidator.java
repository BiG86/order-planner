package it.snorcini.dev.orderplanner.validation;

/**
 * Base constraint typed validator class.
 */
public abstract class AbstractBaseConstraintValidator<T> {

    /**
     * Initialization method to initialize validator.
     *
     * @param typedConstraint typed constraint related to the extended validator
     */
    public void initialize(final T typedConstraint) {
    }
}
