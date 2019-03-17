package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.BatchInfo;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluation;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * Test for BeerArticle.
 */
public class TestBeerArticle {

    private static final UnitOfMeasure UOM = UnitOfMeasure.Kilogram;
    private final Integer id = 1;
    private final String name = "DummyName";

    private Batch batch;

    /**
     * Initialization method.
     */
    @Before
    public void init() {
        batch = new Batch() {
            @Override
            public BatchInfo getBatchInfo() {
                return null;
            }

            @Override
            public Step getCurrentStep() {
                return null;
            }

            @Override
            public List<Step> getLastSteps() {
                return null;
            }

            @Override
            public Result<Empty> moveToNextStep(final StepType nextStepType) {
                return null;
            }

            @Override
            public boolean isEnded() {
                return false;
            }

            @Override
            public Result<Empty> setEvaluation(final BatchEvaluation evaluation) {
                return null;
            }

            @Override
            public Optional<BatchEvaluation> getEvaluation() {
                return Optional.empty();
            }
        };
    }

    /**
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final Article beerArticle = new BeerArticleImpl(id, name, UOM, batch);
        Assert.assertEquals(ArticleType.FINISHED_BEER, beerArticle.getArticleType());
        Assert.assertTrue(beerArticle.toBeerArticle().isPresent());
        Assert.assertEquals(BeerArticleImpl.class, beerArticle.toBeerArticle()
                                                              .getValue()
                                                              .getClass());
        Assert.assertEquals(batch, beerArticle.toBeerArticle().getValue().getBatch());
    }
    /**
     * Method that tests the equals method.
     */
    @Test
    public void testEquals() {
        final BeerArticle beerArt1 = new BeerArticleImpl(id, name, UOM, batch);
        final BeerArticle beerArt2 = new BeerArticleImpl(id, name, UOM, batch);
        final BeerArticle beerArt4 = new BeerArticleImpl(id + 1, name, UOM, batch);
        Assert.assertEquals(beerArt1, beerArt2);
        Assert.assertEquals(beerArt1, beerArt1);
        Assert.assertEquals(beerArt1, beerArt1);
        Assert.assertNotEquals(beerArt1, beerArt4);
        Assert.assertNotEquals(beerArt4, beerArt1);
    }

}
