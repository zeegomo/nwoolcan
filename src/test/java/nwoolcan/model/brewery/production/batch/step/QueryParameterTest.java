package nwoolcan.model.brewery.production.batch.step;

import org.junit.Assert;
import org.junit.Before;
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

    private QueryParameter query;

    /**
     * Sets up fields.
     */
    @Before
    public void setUp() {
        this.query = new QueryParameter();
    }

    /**
     * Simple creation test.
     */
    @Test
    public void simpleCreationTest() {
        Assert.assertFalse(this.query.getParameterType().isPresent());
        Assert.assertFalse(this.query.getGreaterThanValue().isPresent());
        Assert.assertFalse(this.query.getLessThanValue().isPresent());
        Assert.assertFalse(this.query.getExactValue().isPresent());
        Assert.assertFalse(this.query.getStartDate().isPresent());
        Assert.assertFalse(this.query.getEndDate().isPresent());
        Assert.assertFalse(this.query.isSortByValue());
        Assert.assertFalse(this.query.isSortByDate());
        Assert.assertFalse(this.query.isSortDescending());
    }

    /**
     * Complete creation test.
     */
    @Test
    public void completeConstructionTest() {
        query = query.parameterType(TEMP)
                     .greaterThanValue(GTV)
                     .lessThanValue(LTV)
                     .exactValue(EV)
                     .startDate(SD)
                     .endDate(ED);

        Assert.assertTrue(this.query.getParameterType().isPresent());
        Assert.assertTrue(this.query.getGreaterThanValue().isPresent());
        Assert.assertTrue(this.query.getLessThanValue().isPresent());
        Assert.assertTrue(this.query.getExactValue().isPresent());
        Assert.assertTrue(this.query.getStartDate().isPresent());
        Assert.assertTrue(this.query.getEndDate().isPresent());

        Assert.assertEquals(TEMP, this.query.getParameterType().get());
        Assert.assertEquals(GTV, this.query.getGreaterThanValue().get());
        Assert.assertEquals(LTV, this.query.getLessThanValue().get());
        Assert.assertEquals(EV, this.query.getExactValue().get());
        Assert.assertEquals(SD, this.query.getStartDate().get());
        Assert.assertEquals(ED, this.query.getEndDate().get());
    }

    /**
     * Sorting assignments test.
     */
    @Test
    public void sortingTest() {
        query = query.sortByValue(true)
                     .sortByDate(true)
                     .sortDescending(true);

        Assert.assertTrue(this.query.isSortByValue());
        Assert.assertTrue(this.query.isSortByDate());
        Assert.assertTrue(this.query.isSortDescending());
    }
}
