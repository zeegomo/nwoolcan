package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStockBuilder;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Test for Warehouse Implementation.
 */
public class WarehouseImplTest {

    private static final int ONE = 1;
    private static final int TEN = 10;
    private static final String NAME = "DummyName";
    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.MILLILITER;
    private final Warehouse warehouse = new WarehouseImpl();
    private final Article article = warehouse.createMiscArticle(NAME, UOM);
    private final Quantity quantity = Quantity.of(ONE, UOM);
    private final Quantity quantity1 = Quantity.of(ONE, UOM1);
    private final Quantity quantity2 = Quantity.of(TEN, UOM);
    private final Record record = new Record(quantity, Record.Action.ADDING);
    private final Record record1 = new Record(quantity, new Date(), Record.Action.ADDING);
    private final Record record2 = new Record(quantity1, Record.Action.ADDING);
    private static final int MIN_ID = 1;
    private static final int MAX_ID = 1;
    private static final String MIN_NAME = "DummyName";
    private static final String MAX_NAME = "DummyName2";
    private Date date1 = new Date();
    private Date date2 = new Date(date1.getTime() + 10L);
    private Date date3 = new Date(date2.getTime() + 10L);

    /**
     * Initialize the warehouse.
     */
    @Before
    public void initWarehouse() {
        final Result<Stock> stockResult = warehouse.createStock(article);
        final Result<Stock> stockResult1 = warehouse.createStock(article, date1);
        final Result<Stock> stockResult2 = warehouse.createStock(article, date2);
        final Result<Stock> stockResult3 = warehouse.createStock(article, date3);
        Assert.assertTrue(stockResult.isPresent());
        Assert.assertTrue(stockResult1.isPresent());
        Assert.assertTrue(stockResult2.isPresent());
        Assert.assertTrue(stockResult3.isPresent());
        final Stock stock = stockResult.getValue();
        final Stock stock1 = stockResult1.getValue();
        final Stock stock2 = stockResult2.getValue();
        final Stock stock3 = stockResult3.getValue();
        warehouse.addRecord(stock, record);
        warehouse.addRecord(stock, record1);
        warehouse.addRecord(stock1, record);
        warehouse.addRecord(stock2, record);
        warehouse.addRecord(stock3, record);
    }
    /**
     * Test the adders.
     */
    @Test
    public void testAdders() {
        final Result<Stock> stockResult = warehouse.createStock(article);
        Assert.assertTrue(stockResult.isPresent());
        final Stock stock = stockResult.getValue();
        Assert.assertTrue(warehouse.addRecord(stock, record).isPresent());
        Assert.assertTrue(warehouse.addRecord(stock, record1).isPresent());
        Assert.assertTrue(warehouse.addRecord(stock, record2).isError());
    }
    /**
     * Test the stocks getter.
     */
    @Test
    public void testRemainingQuantityWorkingStocksGetter() {
        final Result<QueryStock> resQueryStock = new QueryStockBuilder().setArticle(article)
                                                                        .setMinRemainingQuantity(quantity)
                                                                        .setMaxRemainingQuantity(quantity2)
                                                                        .build();
        Assert.assertTrue(resQueryStock.isPresent());
        final QueryStock queryStock = resQueryStock.getValue();
        final List<Stock> lisStock = warehouse.getStocks(queryStock);
        for (final Stock s : lisStock) {
            Assert.assertEquals(article, s.getArticle());
            Assert.assertFalse(s.getRemainingQuantity().lessThan(quantity));
            Assert.assertFalse(s.getRemainingQuantity().moreThan(quantity2));
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
        final List<Stock> lisStock = warehouse.getStocks(queryStock);
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
        final List<Stock> lisStock = warehouse.getStocks(queryStock);
        Result.of(lisStock.stream()
                        .map(Stock::getRemainingQuantity)
                        .reduce((prev, curr) -> {
                            Assert.assertFalse(curr.moreThan(prev));
                            return curr;
                        }));
    }
    /**
     * Tests getStocks with filter by expiration dates.
     */
    @Test
    public void testExpirationDatesFilters() {
        final Result<QueryStock> resQueryStock = new QueryStockBuilder().setArticle(article)
                                                                        .setExpireAfter(date2)
                                                                        .build();
        Assert.assertTrue(resQueryStock.isPresent());
        final QueryStock queryStock = resQueryStock.getValue();
        final List<Stock> lisStock = warehouse.getStocks(queryStock);
        for (final Stock s : lisStock) {
            Assert.assertEquals(article, s.getArticle());
            Assert.assertTrue(!s.getExpirationDate()
                                 .isPresent()
                           || !s.getExpirationDate()
                                 .get()
                                 .before(date2));
        }

    }
    /**
     * Test getArticles with filter by id.
     */
    @Test
    public void testGetArticlesFilterById() {
        final QueryArticle queryArticle = new QueryArticleBuilder().setMinID(MIN_ID)
                                                                   .setMaxID(MAX_ID)
                                                                   .build();
        final List<Article> lisArticle = warehouse.getArticles(queryArticle);
        for (final Article a : lisArticle) {
            Assert.assertTrue(a.getId() >= MIN_ID);
            Assert.assertTrue(a.getId() <= MAX_ID);
        }
    }
    /**
     * Test getArticles with filter by name.
     */
    @Test
    public void testGetArticlesFilterByName() {
        final QueryArticle queryArticle = new QueryArticleBuilder().setMinName(MIN_NAME)
                                                                   .setMaxName(MAX_NAME)
                                                                   .build();
        final List<Article> lisArticle = warehouse.getArticles(queryArticle);
        for (final Article a : lisArticle) {
            Assert.assertTrue(a.getName().compareTo(MIN_NAME) >= 0);
            Assert.assertTrue(a.getName().compareTo(MAX_NAME) <= 0);
        }
    }
}
