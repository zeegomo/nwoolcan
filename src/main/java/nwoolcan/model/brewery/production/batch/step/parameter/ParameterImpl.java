package nwoolcan.model.brewery.production.batch.step.parameter;

import java.util.Date;
import java.util.Objects;

/**
 * Basic implementation of {@link Parameter} interface.
 */
public final class ParameterImpl implements Parameter {

    private final ParameterType parameterType;
    private final Number registrationValue;
    private final Date registrationDate;

    /**
     * Basic constructor for the class.
     * @param parameterType the parameter's type.
     * @param registrationValue the parameters' registered value.
     * @param registrationDate the parameter's date of registration.\
     */
    public ParameterImpl(final ParameterType parameterType,
                         final Number registrationValue,
                         final Date registrationDate) {
        this.parameterType = parameterType;
        this.registrationValue = registrationValue;
        this.registrationDate = registrationDate;
    }

    /**
     * Constructor that sets the registration date to now.
     * @param parameterType the parameter's type.
     * @param registrationValue the parameters' registered value.
     */
    public ParameterImpl(final ParameterType parameterType, final Number registrationValue) {
        this(parameterType, registrationValue, new Date());
    }

    @Override
    public ParameterType getType() {
        return this.parameterType;
    }

    @Override
    public Number getRegistrationValue() {
        return this.registrationValue;
    }

    @Override
    public Date getRegistrationDate() {
        return new Date(this.registrationDate.getTime());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParameterImpl parameter = (ParameterImpl) o;
        return parameterType.equals(parameter.parameterType)
            && registrationValue.equals(parameter.registrationValue)
            && registrationDate.equals(parameter.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterType, registrationValue, registrationDate);
    }

    @Override
    public String toString() {
        return "[ParameterImpl] {"
            + "parameterType=" + parameterType
            + ", registrationValue=" + registrationValue
            + ", registrationDate=" + registrationDate
            + '}';
    }
}
