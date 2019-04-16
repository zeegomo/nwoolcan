package nwoolcan.viewmodel.brewery.production.step;

import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.utils.UnitOfMeasure;

import java.util.Date;

/**
 * View model representing a {@link Parameter}.
 */
public class ParameterViewModel {

    private final String name;
    private final Number value;
    private final UnitOfMeasure unitOfMeasure;
    private final Date registrationDate;

    /**
     * Basic constructor with decorator-like pattern.
     * @param parameter the parameter to get data from.
     */
    public ParameterViewModel(final Parameter parameter) {
        this.name = parameter.getType().getName();
        this.value = parameter.getRegistrationValue();
        this.unitOfMeasure = parameter.getType().getUnitOfMeasure();
        this.registrationDate = new Date(parameter.getRegistrationDate().getTime());
    }

    /**
     * Returns the name of the parameter type.
     * @return the name of the parameter type.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the registration value.
     * @return the registration value.
     */
    public Number getValue() {
        return this.value;
    }

    /**
     * Returns the unit of measure of the parameter type.
     * @return the unit of measure of the parameter type.
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    /**
     * Returns a string representation of the value with its unit of measure.
     * @return a string representation of the value with its unit of measure.
     */
    public String getValueRepresentation() {
        return String.format("%.2f", this.value.doubleValue()) + " " + this.unitOfMeasure.getSymbol();
    }

    /**
     * Returns the registration date of the parameter.
     * @return the registration date of the parameter.
     */
    public Date getRegistrationDate() {
        return new Date(this.registrationDate.getTime());
    }
}
