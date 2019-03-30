package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientArticleImpl;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Test;

public class BatchBuilderTest {

    private static final BeerDescription bd = new BeerDescriptionImpl("test description", "test style", "test category");
    private static final BatchMethod bm = BatchMethod.ALL_GRAIN;
    private static final Quantity initSize = Quantity.of(10000, UnitOfMeasure.MILLILITER);

    @Test
    public void testSimpleBuild() {
        Result<Batch> res = new BatchBuilder(
            bd,
            bm,
            initSize,
            StepTypeEnum.MASHING
        ).build();

        Assert.assertTrue(res.isPresent());
    }

    @Test
    public void testWrongInitialStep() {
        Result<Batch> res = new BatchBuilder(
            bd,
            bm,
            initSize,
            new StepType() {
                @Override
                public String getName() {
                    return "oops";
                }

                @Override
                public boolean isEndType() {
                    return false;
                }
            }
        ).build();

        Assert.assertTrue(res.isError());
    }

    @Test
    public void testSameIngredientTwice() {
        final IngredientArticle ing = new IngredientArticleImpl("test", UnitOfMeasure.GRAM, IngredientType.OTHER);

        Result<Batch> res = new BatchBuilder(
            bd,
            bm,
            initSize,
            StepTypeEnum.MASHING
        ).addIngredient(ing, Quantity.of(1, UnitOfMeasure.GRAM))
         .addIngredient(ing, Quantity.of(2, UnitOfMeasure.GRAM)).build();

        Assert.assertTrue(res.isError());
    }

    @Test
    public void testIngredientWithWrongUnitOfMeasure() {
        final IngredientArticle ing = new IngredientArticleImpl("test", UnitOfMeasure.GRAM, IngredientType.OTHER);

        Result<Batch> res = new BatchBuilder(
            bd,
            bm,
            initSize,
            StepTypeEnum.MASHING
        ).addIngredient(ing, Quantity.of(1, UnitOfMeasure.MILLILITER)).build();

        Assert.assertTrue(res.isError());
    }
}