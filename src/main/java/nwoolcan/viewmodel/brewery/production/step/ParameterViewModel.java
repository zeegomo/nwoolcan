package nwoolcan.viewmodel.brewery.production.step;

import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.utils.UnitOfMeasure;

import java.util.Date;

public class ParameterViewModel {

    private final String name;
    private final Number value;
    private final UnitOfMeasure unitOfMeasure;
    private final Date registrationDate;

    public ParameterViewModel(final Parameter parameter) {
        this.name = parameter.getType().getName();
        this.value = parameter.getRegistrationValue();
        this.unitOfMeasure = parameter.getType().getUnitOfMeasure();
        this.registrationDate = new Date(parameter.getRegistrationDate().getTime());
    }

    public String getName() {
        return this.name;
    }

    public Number getValue() {
        return this.value;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public String getValueRepresentation() {
        return String.format("%.2f", this.value.doubleValue()) + " " + this.unitOfMeasure.getSymbol();
    }

    public Date getRegistrationDate() {
        return new Date(this.registrationDate.getTime());
    }
}
