package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.IdGenerator;
import nwoolcan.model.brewery.batch.misc.BeerDescription;
import nwoolcan.model.brewery.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.batch.step.StepType;
import nwoolcan.model.brewery.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
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

    private final ArticleManager articleManager = new ArticleManager();
    private static final int TEN_THOUSAND = 10000;
    private static final BeerDescription BD = new BeerDescriptionImpl("test description", "test style", "test category");
    private static final BatchMethod BM = BatchMethod.ALL_GRAIN;
    private static final Quantity INIT_SIZE = Quantity.of(TEN_THOUSAND, UnitOfMeasure.LITER).getValue();

    private final BatchBuilder builder = new BatchBuilder(new IdGenerator() {
        private int nextId = 0;

        @Override
        public int getNextId() {
            return nextId++;
        }
    });

    /**
     * Test simple build.
     */
    @Test
    public void testSimpleBuild() {
        Result<Batch> res = builder.build(
            BD,
            BM,
            INIT_SIZE,
            StepTypeEnum.MASHING
        );

        Assert.assertTrue(res.isPresent());
    }

    /**
     * Test build with wrong initial step type.
     */
    @Test
    public void testWrongInitialStep() {
        Result<Batch> res = builder.build(
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
        );

        Assert.assertTrue(res.isError());
    }

    /**
     * Test build inserting same ingredient twice.
     */
    @Test
    public void testSameIngredientTwice() {
        final IngredientArticle ing = articleManager.createIngredientArticle("test", UnitOfMeasure.GRAM, IngredientType.OTHER);

        Result<Batch> res = builder.addIngredient(ing, 1)
                                   .addIngredient(ing, 2).build(
                BD,
                BM,
                INIT_SIZE,
                StepTypeEnum.MASHING
            );

        Assert.assertTrue(res.isError());
    }
}
