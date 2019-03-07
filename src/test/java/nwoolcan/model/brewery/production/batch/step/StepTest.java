package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Test class for {@link Step} interface.
 */
public class StepTest {

    private Step boiling;
    private Step mashing;
    private Step packaging;

    private static final Quantity Q1 = Quantity.of(10, UnitOfMeasure.Liter);
    private static final Date D1 = new Date(1000);

    private static final List<Parameter> MASHING_PARAMS = Arrays.asList(
        new ParameterImpl(ParameterTypeEnum.Temperature, 10),
        new ParameterImpl(ParameterTypeEnum.Temperature, 10.1),
        new ParameterImpl(ParameterTypeEnum.Temperature, 9.1, D1)
    );

    private static final List<Parameter> BOILING_PARAMS = Arrays.asList(
        new ParameterImpl(ParameterTypeEnum.Temperature, 10.1),
        new ParameterImpl(ParameterTypeEnum.AddedHops, 3)
    );

    /**
     * Initialization.
     */
    @Before
    public void init() {
        this.boiling = new BasicStepImpl(StepTypeEnum.Boiling);
        this.mashing = new BasicStepImpl(StepTypeEnum.Mashing);
        this.packaging = new BasicStepImpl(StepTypeEnum.Packaging, new Date());
    }

    /**
     * Test null step type.
     */
    @Test (expected = NullPointerException.class)
    public void testNullStepType() {
        Step error = new BasicStepImpl(null);
    }

    /**
     * Test null start date.
     */
    @Test (expected = NullPointerException.class)
    public void testNullStartDate() {
        Step error = new BasicStepImpl(StepTypeEnum.Aging, null);
    }

    /**
     * Test various finalization scenarios.
     */
    @Test
    public void testFinalization() {
        Assert.assertFalse(this.boiling.isFinalized());

        final Date endDate = new Date();
        Result<Empty> finRes = this.boiling.finalize("Finalized", endDate, Q1);
        Assert.assertFalse(finRes.isError());
        Assert.assertTrue(this.boiling.isFinalized());

        finRes = this.boiling.finalize("Finalized", new Date(), Q1);
        Assert.assertTrue(finRes.isError() && finRes.getError().getClass() == IllegalStateException.class);

        finRes = this.mashing.finalize(null, null, Q1);
        Assert.assertTrue(finRes.isError() && finRes.getError().getClass() == NullPointerException.class);

        finRes = this.mashing.finalize(null, new Date(), null);
        Assert.assertTrue(finRes.isError() && finRes.getError().getClass() == NullPointerException.class);

        finRes = this.mashing.finalize(null, new Date(this.mashing.getStepInfo().getStartDate().getTime() - 1000), Q1);
        Assert.assertTrue(finRes.isError() && finRes.getError().getClass() == IllegalArgumentException.class);

        Assert.assertTrue(this.boiling.getStepInfo().getNote().isPresent());
        Assert.assertTrue(this.boiling.getStepInfo().getEndDate().isPresent());
        Assert.assertTrue(this.boiling.getStepInfo().getEndStepSize().isPresent());

        finRes = this.mashing.finalize(null, new Date(), Q1);

        Assert.assertTrue(finRes.isPresent());
        Assert.assertFalse(this.mashing.getStepInfo().getNote().isPresent());
        Assert.assertTrue(this.mashing.getStepInfo().getEndDate().isPresent());
        Assert.assertTrue(this.mashing.getStepInfo().getEndStepSize().isPresent());
    }

    private Result<Empty> addParameters() {
        final Number n1 = 10;
        final Number n2 = 10.1;
        final Number n3 = 9.1;

        Result<Empty> res = Result.ofEmpty();

        for (Parameter p : MASHING_PARAMS) {
            res = res.flatMap(() -> this.mashing.addParameter(p));
        }
        for (Parameter p : BOILING_PARAMS) {
            res = res.flatMap(() -> this.boiling.addParameter(p));
        }

        return res;
    }

    /**
     * Simple test for parameter additions.
     */
    @Test
    public void testSimpleParameterAddition() {
        Result<Empty> res = addParameters();
        Assert.assertFalse(res.isError());

        Assert.assertEquals(MASHING_PARAMS, this.mashing.getParameters(new QueryParameter()).getValue());
        Assert.assertEquals(BOILING_PARAMS, this.boiling.getParameters(new QueryParameter()).getValue());
    }

    /**
     * Test wrong parameters addition.
     */
    @Test
    public void testWrongParametersAddition() {
        Result<Empty> res = this.mashing.addParameter(new ParameterImpl(ParameterTypeEnum.AddedHops, 1));
        Assert.assertTrue(res.isError() && res.getError().getClass() == IllegalArgumentException.class);
        res = this.packaging.addParameter(new ParameterImpl(ParameterTypeEnum.Temperature, 1));
        Assert.assertTrue(res.isError() && res.getError().getClass() == IllegalArgumentException.class);
    }

    /**
     * Test adding parameters when step is finalized.
     */
    @Test
    public void testAddingParametersWhenFinalized() {
        this.boiling.finalize(null, new Date(), Q1);
        Assert.assertTrue(this.boiling.isFinalized());
        Result<Empty> res = this.boiling.addParameter(new ParameterImpl(
            ParameterTypeEnum.Temperature, 10
        ));

        Assert.assertTrue(res.isError() && res.getError().getClass() == IllegalStateException.class);
    }

    /**
     * Test query parameters.
     */
    @Test
    public void testQueryParameters() {
        addParameters();

        Result<Collection<Parameter>> res = this.mashing.getParameters(null);
        Assert.assertTrue(res.isError() && res.getError().getClass() == NullPointerException.class);

        res = this.mashing.getParameters(new QueryParameter().sortByValue(true));
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(MASHING_PARAMS.stream().sorted((p1, p2) -> Double.compare(p1.getRegistrationValue().doubleValue(), p2.getRegistrationValue().doubleValue()))
                                                   .collect(Collectors.toList()), res.getValue());

        res = this.mashing.getParameters(new QueryParameter().sortByValue(true).sortDescending(true));
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(MASHING_PARAMS.stream().sorted((p1, p2) -> -Double.compare(p1.getRegistrationValue().doubleValue(), p2.getRegistrationValue().doubleValue()))
                                          .collect(Collectors.toList()), res.getValue());

        final double val = 9.9;
        res = this.mashing.getParameters(new QueryParameter().greaterThanValue(val));
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(MASHING_PARAMS.stream().filter(p -> p.getRegistrationValue().doubleValue() > val).collect(Collectors.toList()),
            res.getValue());

        res = this.mashing.getParameters(new QueryParameter().parameterType(ParameterTypeEnum.Temperature));
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(MASHING_PARAMS, res.getValue());

        final double val2 = 9.1;
        res = this.mashing.getParameters(new QueryParameter().lessThanValue(val));
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(Collections.singletonList(new ParameterImpl(ParameterTypeEnum.Temperature, val2, D1)), res.getValue());

        res = this.mashing.getParameters(new QueryParameter().exactValue(val2));
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(Collections.singletonList(new ParameterImpl(ParameterTypeEnum.Temperature, val2, D1)), res.getValue());
    }
}
