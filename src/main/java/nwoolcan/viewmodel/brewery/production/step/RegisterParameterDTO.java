package nwoolcan.viewmodel.brewery.production.step;

import nwoolcan.model.brewery.batch.step.parameter.ParameterType;

import java.util.Date;

/**
 * DTO representing data to register a parameter in a step.
 */
public class RegisterParameterDTO {

    private final int batchId;
    private final double value;
    private final ParameterType type;
    private final Date registrationDate;

    /**
     * Basic constructor.
     * @param batchId the step's batch id.
     * @param value the registered value.
     * @param type the type of the registered parameter.
     * @param registrationDate the parameter registration date.
     */
    public RegisterParameterDTO(final int batchId,
                                final double value,
                                final ParameterType type,
                                final Date registrationDate) {
        this.batchId = batchId;
        this.value = value;
        this.type = type;
        this.registrationDate = new Date(registrationDate.getTime());
    }

    /**
     * Returns the step's batch id.
     * @return the step's batch id.
     */
    public int getBatchId() {
        return this.batchId;
    }

    /**
     * Returns the step's batch id.
     * @return the step's batch id.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Returns the type of the registered parameter.
     * @return the type of the registered parameter.
     */
    public ParameterType getType() {
        return this.type;
    }

    /**
     * Returns the registration date.
     * @return the registration date.
     */
    public Date getRegistrationDate() {
        return new Date(this.registrationDate.getTime());
    }
}
