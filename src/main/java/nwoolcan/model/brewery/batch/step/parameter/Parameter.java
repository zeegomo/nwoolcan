package nwoolcan.model.brewery.batch.step.parameter;

import java.util.Date;

/**
 * Interface that keeps track of a parameter registered in the production process.
 */
public interface Parameter {

    /**
     * Returns the {@link ParameterType} of this parameter.
     * @return the {@link ParameterType} of this parameter.
     */
    ParameterType getType();

    /**
     * Returns the value of the registration.
     * @return the value of the registration.
     */
    Number getRegistrationValue();

    /**
     * Returns the date of the registration.
     * @return the date of the registration.
     */
    Date getRegistrationDate();
}
