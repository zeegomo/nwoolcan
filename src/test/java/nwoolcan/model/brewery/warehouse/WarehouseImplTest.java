package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.ArticleImpl;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStockBuilder;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
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
    private final Article article = new ArticleImpl(NAME, UOM);
    private final Article article1 = new ArticleImpl(NAME, UOM);
    private final Quantity quantity = Quantity.of(ONE, UOM);
    private final Quantity quantity1 = Quantity.of(ONE, UOM1);
    private final Quantity quantity2 = Quantity.of(TEN, UOM);
    private final Quantity quantity3 = Quantity.of(TEN * TEN, UOM);
    private final Record record = new Record(quantity, Record.Action.ADDING);
    private final Record record1 = new Record(quantity, new Date(), Record.Action.ADDING);
    private final Record record2 = new Record(quantity1, Record.Action.ADDING);
    private final Record record3 = new Record(quantity2, Record.Action.ADDING);
    private final Record record4 = new Record(quantity3, Record.Action.ADDING);
    private static final Integer MIN_ID = 1;
    private static final Integer MAX_ID = 1;
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
     * Tests getStocks with filter by expiration dates.
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
        final Result<List<Article>> resLisArticle = warehouse.getArticles(queryArticle);
        Assert.assertTrue(resLisArticle.isPresent());
        final List<Article> lisArticle = resLisArticle.getValue();
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
        final Result<List<Article>> resLisArticle = warehouse.getArticles(queryArticle);
        Assert.assertTrue(resLisArticle.isPresent());
        final List<Article> lisArticle = resLisArticle.getValue();
        for (final Article a : lisArticle) {
            Assert.assertTrue(a.getName().compareTo(MIN_NAME) >= 0);
            Assert.assertTrue(a.getName().compareTo(MAX_NAME) <= 0);
        }
    }
}
