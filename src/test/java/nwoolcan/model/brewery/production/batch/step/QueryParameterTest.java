package nwoolcan.model.brewery.production.batch.step;

import nwoolcan.model.brewery.production.batch.step.parameter.ParameterType;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.production.batch.step.parameter.QueryParameter;
import nwoolcan.model.brewery.production.batch.step.parameter.QueryParameterBuilder;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for {@link QueryParameter}.
 */
public class QueryParameterTest {

    private static final ParameterType TEMP = ParameterTypeEnum.Temperature;
    private static final Number GTV = 5;
    private static final Number LTV = 50;
    private static final Number EV = 10;
    private static final Date SD = new Date();
    private static final Date ED = new Date(SD.getTime() + 1000);

    /**
     * Simple build test.
     */
    @Test
    public void testSimpleCreation() {
        final Result<QueryParameter> res = new QueryParameterBuilder().build();

        Assert.assertTrue(res.isPresent());

        final QueryParameter query = res.getValue();

        Assert.assertFalse(query.getParameterType().isPresent());
        Assert.assertFalse(query.getGreaterThanValue().isPresent());
        Assert.assertFalse(query.getLessThanValue().isPresent());
        Assert.assertFalse(query.getExactValue().isPresent());
        Assert.assertFalse(query.getStartDate().isPresent());
        Assert.assertFalse(query.getEndDate().isPresent());
        Assert.assertFalse(query.isSortByValue());
        Assert.assertFalse(query.isSortByDate());
        Assert.assertFalse(query.isSortDescending());
    }

    /**
     * Complete build test.
     */
    @Test
    public void testCompleteConstruction() {
        final Result<QueryParameter> res = new QueryParameterBuilder().parameterType(TEMP)
                                                                      .greaterThanValue(GTV)
                                                                      .lessThanValue(LTV)
                                                                      .startDate(SD)
                                                                      .endDate(ED)
                                                                      .sortByValue(true)
                                                                      .sortDescending(true)
                                                                      .build();
        Assert.assertTrue(res.isPresent());

        final QueryParameter query = res.getValue();

        Assert.assertTrue(query.getParameterType().isPresent());
        Assert.assertTrue(query.getGreaterThanValue().isPresent());
        Assert.assertTrue(query.getLessThanValue().isPresent());
        Assert.assertTrue(query.getStartDate().isPresent());
        Assert.assertTrue(query.getEndDate().isPresent());

        Assert.assertEquals(TEMP, query.getParameterType().get());
        Assert.assertEquals(GTV, query.getGreaterThanValue().get());
        Assert.assertEquals(LTV, query.getLessThanValue().get());
        Assert.assertEquals(SD, query.getStartDate().get());
        Assert.assertEquals(ED, query.getEndDate().get());
        Assert.assertTrue(query.isSortByValue());
        Assert.assertTrue(query.isSortDescending());
    }

    /**
     * Wrong build test.
     */
    @Test
    public void testWrongCreation() {
        Result<QueryParameter> res = new QueryParameterBuilder().greaterThanValue(1)
                                                                .lessThanValue(0)
                                                                .build();
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());
        System.out.println(res.getError().getMessage());

        res = new QueryParameterBuilder().exactValue(EV).greaterThanValue(GTV).build();
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());
        System.out.println(res.getError().getMessage());

        res = new QueryParameterBuilder().startDate(ED).endDate(SD).build();
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());
        System.out.println(res.getError().getMessage());

        res = new QueryParameterBuilder().sortByDate(true).sortByValue(true).build();
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());
        System.out.println(res.getError().getMessage());
    }
}
