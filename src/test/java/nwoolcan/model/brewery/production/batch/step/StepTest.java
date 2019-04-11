package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.brewery.production.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.production.batch.step.parameter.QueryParameterBuilder;
import nwoolcan.model.brewery.production.batch.step.parameter.QueryParameter;
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
import java.util.Comparator;

/**
 * Test class for {@link Step} interface.
 */
public class StepTest {

    private Step boiling;
    private Step mashing;
    private Step packaging;
    private Step finalized;

    private static final Quantity Q1 = Quantity.of(10, UnitOfMeasure.MILLILITER).getValue();
    private static final Date D1 = new Date(1000);

    private static final List<Parameter> MASHING_PARAMS = Arrays.asList(
        new ParameterImpl(ParameterTypeEnum.TEMPERATURE, 10),
        new ParameterImpl(ParameterTypeEnum.TEMPERATURE, 10.1),
        new ParameterImpl(ParameterTypeEnum.TEMPERATURE, 9.1, D1)
    );

    private static final List<Parameter> BOILING_PARAMS = Arrays.asList(
        new ParameterImpl(ParameterTypeEnum.TEMPERATURE, 10.1)
    );

    /**
     * Initialization.
     */
    @Before
    public void init() {
        this.boiling = new BasicStep(StepTypeEnum.BOILING);
        this.mashing = new BasicStep(StepTypeEnum.MASHING);
        this.packaging = new BasicStep(StepTypeEnum.PACKAGING, new Date());
        this.finalized = new BasicStep(StepTypeEnum.FINALIZED);
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
        Assert.assertTrue(finRes.isError());
        Assert.assertSame(IllegalStateException.class, finRes.getError().getClass());

        finRes = this.mashing.finalize(null, new Date(this.mashing.getStepInfo().getStartDate().getTime() - 1000), Q1);
        Assert.assertTrue(finRes.isError());
        Assert.assertSame(IllegalArgumentException.class, finRes.getError().getClass());

        Assert.assertTrue(this.boiling.getStepInfo().getNote().isPresent());
        Assert.assertTrue(this.boiling.getStepInfo().getEndDate().isPresent());
        Assert.assertTrue(this.boiling.getStepInfo().getEndStepSize().isPresent());

        finRes = this.mashing.finalize(null, new Date(), Q1);

        Assert.assertFalse(finRes.isError());
        Assert.assertFalse(this.mashing.getStepInfo().getNote().isPresent());
        Assert.assertTrue(this.mashing.getStepInfo().getEndDate().isPresent());
        Assert.assertTrue(this.mashing.getStepInfo().getEndStepSize().isPresent());

        finRes = this.packaging.finalize("Ciao", new Date(), Q1);
        Assert.assertFalse(finRes.isError());

        Assert.assertTrue(this.finalized.isFinalized());
        finRes = this.finalized.finalize(null, new Date(), Q1);
        Assert.assertTrue(finRes.isError());
        Assert.assertSame(IllegalStateException.class, finRes.getError().getClass());
    }

    private Result<Empty> addParameters() {
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

        Assert.assertArrayEquals(MASHING_PARAMS.toArray(), this.mashing.getParameters(new QueryParameterBuilder().build().getValue()).toArray());
        Assert.assertArrayEquals(BOILING_PARAMS.toArray(), this.boiling.getParameters(new QueryParameterBuilder().build().getValue()).toArray());
    }

    /**
     * Test wrong parameters addition.
     */
    @Test
    public void testWrongParametersAddition() {
        Result<Empty> res = this.packaging.addParameter(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, 1));
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());
    }

    /**
     * Test adding parameters when step is finalized.
     */
    @Test
    public void testAddingParametersWhenFinalized() {
        this.boiling.finalize(null, new Date(), Q1);
        Assert.assertTrue(this.boiling.isFinalized());
        Result<Empty> res = this.boiling.addParameter(new ParameterImpl(
            ParameterTypeEnum.TEMPERATURE, 10
        ));

        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalStateException.class, res.getError().getClass());

        res = this.finalized.addParameter(new ParameterImpl(
            ParameterTypeEnum.TEMPERATURE, 10
        ));
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalStateException.class, res.getError().getClass());
    }

    /**
     * Test query parameters.
     */
    @Test
    public void testQueryParameters() {
        addParameters();

        Result<QueryParameter> resQ = new QueryParameterBuilder().sortByValue(true).build();
        Assert.assertTrue(resQ.isPresent());
        Collection<Parameter> res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(MASHING_PARAMS.stream()
                                               .sorted(Comparator.comparingDouble(p -> p.getRegistrationValue().doubleValue()))
                                               .toArray(), res.toArray());

        resQ = new QueryParameterBuilder().sortByValue(true).sortDescending(true).build();
        Assert.assertTrue(resQ.isPresent());
        res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(MASHING_PARAMS.stream()
                                               .sorted((p1, p2) -> Double.compare(p2.getRegistrationValue().doubleValue(), p1.getRegistrationValue().doubleValue()))
                                               .toArray(), res.toArray());

        final double val = 9.9;
        resQ = new QueryParameterBuilder().greaterThanValue(val).build();
        Assert.assertTrue(resQ.isPresent());
        res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(MASHING_PARAMS.stream()
                                               .filter(p -> p.getRegistrationValue().doubleValue() > val)
                                               .toArray(), res.toArray());

        resQ = new QueryParameterBuilder().parameterType(ParameterTypeEnum.TEMPERATURE).build();
        Assert.assertTrue(resQ.isPresent());
        res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(MASHING_PARAMS.toArray(), res.toArray());


        final double val2 = 9.1;
        resQ = new QueryParameterBuilder().lessThanValue(val).build();
        Assert.assertTrue(resQ.isPresent());
        res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(Collections.singletonList(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, val2, D1)).toArray(),
            res.toArray());

        resQ = new QueryParameterBuilder().lessThanValue(val2).build();
        Assert.assertTrue(resQ.isPresent());
        res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(Collections.singletonList(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, val2, D1)).toArray(),
            res.toArray());

        resQ = new QueryParameterBuilder().endDate(D1).build();
        Assert.assertTrue(resQ.isPresent());
        res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(Collections.singletonList(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, val2, D1)).toArray(),
            res.toArray());

        resQ = new QueryParameterBuilder().startDate(D1).endDate(new Date()).build();
        Assert.assertTrue(resQ.isPresent());
        res = this.mashing.getParameters(resQ.getValue());
        Assert.assertArrayEquals(MASHING_PARAMS.toArray(), res.toArray());
    }
}
