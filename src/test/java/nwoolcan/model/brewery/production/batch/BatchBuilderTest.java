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

/**
 * Test class for BatchBuilder.
 */
public class BatchBuilderTest {

    private static final BeerDescription BD = new BeerDescriptionImpl("test description", "test style", "test category");
    private static final BatchMethod BM = BatchMethod.ALL_GRAIN;
    private static final Quantity INIT_SIZE = Quantity.of(10000, UnitOfMeasure.MILLILITER);

    /**
     * Test simple build.
     */
    @Test
    public void testSimpleBuild() {
        Result<Batch> res = new BatchBuilder(
            BD,
            BM,
            INIT_SIZE,
            StepTypeEnum.MASHING
        ).build();

        Assert.assertTrue(res.isPresent());
    }

    /**
     * Test build with wrong initial step type.
     */
    @Test
    public void testWrongInitialStep() {
        Result<Batch> res = new BatchBuilder(
            BD,
            BM,
            INIT_SIZE,
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

    /**
     * Test build inserting same ingredient twice.
     */
    @Test
    public void testSameIngredientTwice() {
        final IngredientArticle ing = new IngredientArticleImpl("test", UnitOfMeasure.GRAM, IngredientType.OTHER);

        Result<Batch> res = new BatchBuilder(
            BD,
            BM,
            INIT_SIZE,
            StepTypeEnum.MASHING
        ).addIngredient(ing, 1)
         .addIngredient(ing, 2).build();

        Assert.assertTrue(res.isError());
    }
}
