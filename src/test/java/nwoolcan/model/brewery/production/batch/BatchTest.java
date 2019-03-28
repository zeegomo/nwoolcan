package nwoolcan.model.brewery.production.batch;

import javafx.util.Pair;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepType;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientArticleImpl;
import nwoolcan.model.utils.Quantities;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Test class for Batch.
 */
public class BatchTest {

    private static final int TEN_THOUSAND = 1000;
    private static final Quantity Q1 = Quantity.of(TEN_THOUSAND, UnitOfMeasure.MILLILITER);
    private static final Quantity Q2 = Quantity.of(TEN_THOUSAND - 1, UnitOfMeasure.MILLILITER);

    private Batch batchAlfredo, batchRossina, batchBiondina;
    private BeerDescription alfredo, rossina, biondina;

    private List<Pair<IngredientArticle, Quantity>> alfredoIngredients = Arrays.asList();
    private List<Pair<IngredientArticle, Quantity>> rossinaIngredients = Arrays.asList();
    private List<Pair<IngredientArticle, Quantity>> biondinaIngredients = Arrays.asList();

    /**
     * Init fields.
     */
    @Before
    public void init() {
        alfredo = new BeerDescriptionImpl("Alfredo", "Alfredo style");
        rossina = new BeerDescriptionImpl("Rossina", "Rossina style", "Best category");
        biondina = new BeerDescriptionImpl("Biondina", "Biondina style");

        batchAlfredo = new BatchImpl(
            alfredo,
            BatchMethod.ALL_GRAIN,
            Q1,
            alfredoIngredients,
            StepTypeEnum.MASHING
            );

        batchRossina = new BatchImpl(
            rossina,
            BatchMethod.PARTIAL_MASH,
            Q1,
            rossinaIngredients,
            StepTypeEnum.MASHING
        );

        batchBiondina = new BatchImpl(
            biondina,
            BatchMethod.EXTRACT,
            Q2,
            biondinaIngredients,
            StepTypeEnum.MASHING
        );
    }

    /**
     * Methods that tests passages from one step to another.
     */
    @Test
    public void testChangeStep() {
        //Without finalizing
        Result<Empty> res = batchAlfredo.moveToNextStep(StepTypeEnum.BOILING);
        Assert.assertTrue(res.isPresent());

        Assert.assertEquals(StepTypeEnum.BOILING, batchAlfredo.getCurrentStep().getStepInfo().getType());
        Assert.assertEquals(Q1, batchAlfredo.getBatchInfo().getBatchSize());

        Assert.assertEquals(1, batchAlfredo.getPreviousSteps().size());
        Step prevStep = batchAlfredo.getPreviousSteps().get(0);

        Assert.assertTrue(prevStep.isFinalized());
        Assert.assertFalse(prevStep.getStepInfo().getNote().isPresent());
        Assert.assertEquals(StepTypeEnum.MASHING, prevStep.getStepInfo().getType());
        Assert.assertTrue(prevStep.getStepInfo().getEndDate().isPresent());
        Assert.assertTrue(prevStep.getStepInfo().getEndStepSize().isPresent());
        Assert.assertEquals(Q1, prevStep.getStepInfo().getEndStepSize().get());

        //With finalizing
        String notes = "Dummy notes.";
        Date endDate = new Date();

        batchRossina.getCurrentStep().finalize(notes, endDate, Q2);
        res = batchRossina.moveToNextStep(StepTypeEnum.BOILING);
        Assert.assertTrue(res.isPresent());

        Assert.assertNotEquals(Q2, batchRossina.getBatchInfo().getBatchSize());
        Assert.assertEquals(1, batchRossina.getPreviousSteps().size());
        prevStep = batchRossina.getPreviousSteps().get(0);

        Assert.assertTrue(prevStep.isFinalized());
        Assert.assertTrue(prevStep.getStepInfo().getNote().isPresent());
        Assert.assertEquals(notes, prevStep.getStepInfo().getNote().get());
        Assert.assertTrue(prevStep.getStepInfo().getEndDate().isPresent());
        Assert.assertEquals(endDate, prevStep.getStepInfo().getEndDate().get());
        Assert.assertEquals(Q2, prevStep.getStepInfo().getEndStepSize().get());
    }
}
