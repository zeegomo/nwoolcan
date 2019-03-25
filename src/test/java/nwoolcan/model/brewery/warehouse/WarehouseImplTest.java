package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleImpl;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

import java.util.Date;
import java.util.List;

/**
 * Test for Warehouse Implementation.
 */
public class WarehouseImplTest {

    private static final Integer ONE = 1;
    private static final Integer TEN = 10;
    private static final Double INF = 1e9;
    private static final String NAME = "DummyName";
    private static final UnitOfMeasure UOM = UnitOfMeasure.Kilogram;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.Liter;
    private final Warehouse warehouse = new WarehouseImpl();
    private final Article article = new ArticleImpl(ONE, NAME, UOM);
    private final Article article1 = new ArticleImpl(TEN, NAME, UOM);
    private final Quantity quantity = Quantity.of(ONE, UOM);
    private final Quantity quantity1 = Quantity.of(ONE, UOM1);
    private final Quantity quantity2 = Quantity.of(TEN, UOM);
    private final Quantity quantity3 = Quantity.of(TEN * TEN, UOM);
    private final Record record = new Record(quantity, Record.Action.ADDING);
    private final Record record1 = new Record(quantity, new Date(), Record.Action.ADDING);
    private final Record record2 = new Record(quantity1, Record.Action.ADDING);
    private final Record record3 = new Record(quantity2, Record.Action.ADDING);
    private final Record record4 = new Record(quantity3, Record.Action.ADDING);
    private Date date1;
    private Date date2;
    private Date date3;

    /**
     * Initialize the warehouse.
     */
    @Before
    public void initWarehouse() {
        date1 = new Date();
        Results.ofChecked(() -> Thread.sleep(10L));
        date2 = new Date();
        Results.ofChecked(() -> Thread.sleep(10L));
        date3 = new Date();
        warehouse.addArticle(article);
        warehouse.addArticle(article1);
        warehouse.addRecord(article, record);
        warehouse.addRecord(article, record1);
        warehouse.addRecord(article, date1, record);
        warehouse.addRecord(article, date2, record);
        warehouse.addRecord(article, date3, record);
    }
    /**
     * Test the adders.
     */
    @Test
    public void testAdders() {
        Assert.assertTrue(warehouse.addArticle(article).isError()); // already added
        Assert.assertTrue(warehouse.addRecord(article, record).isPresent());
        Assert.assertTrue(warehouse.addRecord(article, record1).isPresent());
        Assert.assertTrue(warehouse.addRecord(article, record2).isError());
    }
    /**
     * Test the stocks getter.
     */
    @Test
    public void testRemainingQuantityWorkingStocksGetter() {
        Assert.assertTrue(warehouse.addArticle(article).isError()); // already added
        Assert.assertTrue(warehouse.addArticle(article1).isError()); // already added
        final Result<QueryStock> resQueryStock = new QueryStockBuilder().setArticle(article)
                                                                     .setMinRemainingQuantity(quantity)
                                                                     .setMaxRemainingQuantity(quantity2)
                                                                     .build();
        Assert.assertTrue(resQueryStock.isPresent());
        final QueryStock queryStock = resQueryStock.getValue();
        final Result<List<Stock>> resLisStock = warehouse.getStocks(queryStock);
        Assert.assertTrue(resLisStock.isPresent());
        final List<Stock> lisStock = resLisStock.getValue();
        for (final Stock s : lisStock) {
            Assert.assertEquals(article, s.getArticle());
            Assert.assertTrue(s.getRemainingQuantity().getValue().doubleValue() >= quantity.getValue().doubleValue());
            Assert.assertTrue(s.getRemainingQuantity().getValue().doubleValue() <= quantity2.getValue().doubleValue());
            System.out.println(s.toString());
        }

    }
    /**
     * Test the stocks getter when restrictions don't allow any element in the list.
     */
    @Test
    public void testRemainingQuantityStocksWithNoResultingStockGetter() {
        final Result<QueryStock> resQueryStock = new QueryStockBuilder().setArticle(article)
                                                                        .setMinRemainingQuantity(quantity2)
                                                                        .setMaxRemainingQuantity(quantity)
                                                                        .build();
        Assert.assertTrue(resQueryStock.isPresent());
        final QueryStock queryStock = resQueryStock.getValue();
        final Result<List<Stock>> resLisStock = warehouse.getStocks(queryStock);
        Assert.assertTrue(resLisStock.isPresent());
        final List<Stock> lisStock = resLisStock.getValue();
        Assert.assertEquals(0, lisStock.size());
    }
    /**
     * Test the sort getter.
     */
    @Test
    public void testSortedStocksGetter() {
        final Result<QueryStock> resQueryStock = new QueryStockBuilder().setArticle(article)
                                                                        .setMinRemainingQuantity(quantity)
                                                                        .setMaxRemainingQuantity(quantity2)
                                                                        .sortBy(QueryStock.SortParameter.REMAINING_QUANTITY)
                                                                        .sortDescending(true)
                                                                        .build();
        Assert.assertTrue(resQueryStock.isPresent());
        final QueryStock queryStock = resQueryStock.getValue();
        final Result<List<Stock>> resLisStock = warehouse.getStocks(queryStock);
        Assert.assertTrue(resLisStock.isPresent());
        final List<Stock> lisStock = resLisStock.getValue();
        double preVal = INF;
        for (final Stock s : lisStock) {
            Assert.assertEquals(article, s.getArticle());
            Assert.assertTrue(s.getRemainingQuantity().getValue().doubleValue() >= quantity.getValue().doubleValue());
            Assert.assertTrue(s.getRemainingQuantity().getValue().doubleValue() <= quantity2.getValue().doubleValue());
            Assert.assertTrue(s.getRemainingQuantity().getValue().doubleValue() <= preVal);
            preVal = s.getRemainingQuantity().getValue().doubleValue(); // assert it is sorted
        }
    }
    /**
     * Filter by expiration dates.
     */
    @Test
    public void testExpirationDatesFilters() {
        final Result<QueryStock> resQueryStock = new QueryStockBuilder().setArticle(article)
                                                                        .setExpireAfter(date2)
                                                                        .build();
        Assert.assertTrue(resQueryStock.isPresent());
        final QueryStock queryStock = resQueryStock.getValue();
        final Result<List<Stock>> resLisStock = warehouse.getStocks(queryStock);
        Assert.assertTrue(resLisStock.isPresent());
        final List<Stock> lisStock = resLisStock.getValue();
        for (final Stock s : lisStock) {
            Assert.assertEquals(article, s.getArticle());
            Assert.assertTrue(!s.getExpirationDate().isPresent() || !s.getExpirationDate().get().before(date2));
        }

    }
}
