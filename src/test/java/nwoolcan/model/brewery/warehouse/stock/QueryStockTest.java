package nwoolcan.model.brewery.warehouse.stock;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.BreweryImpl;
import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for {@link QueryStock}.
 */
public final class QueryStockTest {
    private static final Brewery BREWERY = new BreweryImpl();
    private static final int TWO = 2;
    private static final String ARTICLE_NAME = "ArticleName";
    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private static final UnitOfMeasure UOM1 = UnitOfMeasure.UNIT;
    private static final Article ARTICLE = BREWERY.getWarehouse().createMiscArticle(ARTICLE_NAME, UOM);
    private static final Date DATE = new Date();
    private static final Quantity QUANTITY = Quantity.of(TWO, UOM).getValue();
    private static final Quantity QUANTITY_WRONG = Quantity.of(TWO, UOM1).getValue();

    /**
     * Simple build test.
     */
    @Test
    public void testSimpleCreation() {
        final Result<QueryStock> res = new QueryStockBuilder().build();
        Assert.assertTrue(res.isPresent());

        final QueryStock query = res.getValue();

        Assert.assertFalse(query.getArticleId().isPresent());
        Assert.assertFalse(query.getExpiresBefore().isPresent());
        Assert.assertFalse(query.getExpiresAfter().isPresent());
        Assert.assertFalse(query.getMinRemainingQuantity().isPresent());
        Assert.assertFalse(query.getMaxRemainingQuantity().isPresent());
        Assert.assertFalse(query.getMinUsedQuantity().isPresent());
        Assert.assertFalse(query.getMaxUsedQuantity().isPresent());
        Assert.assertFalse(query.getIncludeStockState().isPresent());
        Assert.assertFalse(query.getExcludeStockState().isPresent());
        Assert.assertEquals(QueryStock.SortParameter.ID, query.getSortBy());
        Assert.assertFalse(query.isSortDescending());
    }
    /**
     * Complete build test.
     */
    @Test
    public void testCompleteConstruction() {
        Result<QueryStock> res = new QueryStockBuilder().setArticle(AbstractArticleViewModel.getViewArticle(ARTICLE))
                                                        .setExpireBefore(DATE)
                                                        .setExpireAfter(DATE)
                                                        .setMinRemainingQuantity(QUANTITY)
                                                        .setMaxRemainingQuantity(QUANTITY)
                                                        .setMinUsedQuantity(QUANTITY)
                                                        .setMaxUsedQuantity(QUANTITY)
                                                        .setIncludeOnlyStockState(StockState.EXPIRED)
                                                        .setExcludeOnlyStockState(StockState.AVAILABLE)
                                                        .sortDescending(true)
                                                        .build();
        Assert.assertTrue(res.isPresent());

        final QueryStock query = res.getValue();

        Assert.assertTrue(query.getArticleId().isPresent());
        Assert.assertTrue(query.getExpiresBefore().isPresent());
        Assert.assertTrue(query.getExpiresAfter().isPresent());
        Assert.assertTrue(query.getMinRemainingQuantity().isPresent());
        Assert.assertTrue(query.getMaxRemainingQuantity().isPresent());
        Assert.assertTrue(query.getMinUsedQuantity().isPresent());
        Assert.assertTrue(query.getMaxUsedQuantity().isPresent());
        Assert.assertTrue(query.getIncludeStockState().isPresent());
        Assert.assertTrue(query.getExcludeStockState().isPresent());
        Assert.assertTrue(query.isSortDescending());

        Assert.assertEquals((long) ARTICLE.getId(), (long) query.getArticleId().get());
        Assert.assertEquals(DATE, query.getExpiresBefore().get());
        Assert.assertEquals(DATE, query.getExpiresAfter().get());
        Assert.assertEquals(QUANTITY, query.getMinRemainingQuantity().get());
        Assert.assertEquals(QUANTITY, query.getMaxRemainingQuantity().get());
        Assert.assertEquals(QUANTITY, query.getMinUsedQuantity().get());
        Assert.assertEquals(QUANTITY, query.getMaxUsedQuantity().get());
        Assert.assertEquals(StockState.EXPIRED, query.getIncludeStockState().get());
        Assert.assertEquals(StockState.AVAILABLE, query.getExcludeStockState().get());
    }
    /**
     * Wrong build test.
     */
    @Test
    public void testWrongConstruction() {
        Result<QueryStock> res = new QueryStockBuilder().setArticle(AbstractArticleViewModel.getViewArticle(ARTICLE))
                                                        .setMinRemainingQuantity(QUANTITY)
                                                        .setMaxRemainingQuantity(QUANTITY_WRONG)
                                                        .build();
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());

        res = new QueryStockBuilder().setMinRemainingQuantity(QUANTITY)
                                     .setMaxRemainingQuantity(QUANTITY)
                                     .build();
        Assert.assertTrue(res.isError());
        Assert.assertSame(IllegalArgumentException.class, res.getError().getClass());

    }

}
