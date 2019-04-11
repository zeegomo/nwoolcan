package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.Brewery;
import nwoolcan.model.brewery.BreweryImpl;
import nwoolcan.model.brewery.production.batch.misc.BeerDescription;
import nwoolcan.model.brewery.production.batch.misc.BeerDescriptionImpl;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.production.batch.misc.WaterMeasurementFactory;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationBuilder;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationType;
import nwoolcan.model.brewery.production.batch.review.Evaluation;
import nwoolcan.model.brewery.production.batch.review.EvaluationFactory;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.model.brewery.production.batch.step.Step;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterImpl;
import nwoolcan.model.brewery.production.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.utils.Quantities;
import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test class for Batch.
 */
public class BatchTest {

    private static final int TEN_THOUSAND = 10000;
    private static final int N1 = 500;
    private static final int N2 = 50;
    private static final int N3 = 450;
    private static final int N4 = 20;
    private static final int N5 = 1000;
    private static final int N6 = 70;
    private static final Quantity Q1 = Quantity.of(TEN_THOUSAND, UnitOfMeasure.MILLILITER).getValue();
    private static final Quantity Q2 = Quantity.of(TEN_THOUSAND - 1, UnitOfMeasure.MILLILITER).getValue();
    private static final ArticleManager ARTICLE_MANAGER = ArticleManager.getInstance();
    private final BatchEvaluationType bjcpType = BatchEvaluationBuilder.getAvailableBatchEvaluationTypes()
                                                                       .getValue()
                                                                       .stream()
                                                                       .filter(s -> s.getClass().equals(BJCPBatchEvaluationType.class))
                                                                       .findAny().get();
    private final Brewery brewery = new BreweryImpl();

    private Batch batchAlfredo, batchRossina, batchBiondina;

    private List<Pair<IngredientArticle, Integer>> alfredoIngredients = Arrays.asList(
        Pair.of(ARTICLE_MANAGER.createIngredientArticle("Luppolo alfredo", UnitOfMeasure.GRAM, IngredientType.HOPS), N1),
        Pair.of(ARTICLE_MANAGER.createIngredientArticle("Pepe gigio", UnitOfMeasure.GRAM, IngredientType.OTHER), N2)
    );
    private List<Pair<IngredientArticle, Integer>> rossinaIngredients = Arrays.asList(
        Pair.of(ARTICLE_MANAGER.createIngredientArticle("Luppolo rossino", UnitOfMeasure.GRAM, IngredientType.HOPS), N3),
        Pair.of(ARTICLE_MANAGER.createIngredientArticle("Pepe faggio", UnitOfMeasure.GRAM, IngredientType.OTHER), N4)
    );
    private List<Pair<IngredientArticle, Integer>> biondinaIngredients = Arrays.asList(
        Pair.of(ARTICLE_MANAGER.createIngredientArticle("Luppolo biondino", UnitOfMeasure.GRAM, IngredientType.HOPS), N5),
        Pair.of(ARTICLE_MANAGER.createIngredientArticle("Pepe daggio", UnitOfMeasure.GRAM, IngredientType.OTHER), N6)
    );

    /**
     * Init fields.
     */
    @Before
    public void init() {
        final BeerDescription alfredo = new BeerDescriptionImpl("Alfredo", "Alfredo style");
        final BeerDescription rossina = new BeerDescriptionImpl("Rossina", "Rossina style", "Best category");
        final BeerDescription biondina = new BeerDescriptionImpl("Biondina", "Biondina style");

        final BatchBuilder b1 = brewery.getBatchBuilder();
        alfredoIngredients.forEach(i -> b1.addIngredient(i.getLeft(), i.getRight()));

        batchAlfredo = b1.build(
            alfredo,
            BatchMethod.ALL_GRAIN,
            Q1,
            StepTypeEnum.MASHING
        ).getValue();

        final BatchBuilder b2 = brewery.getBatchBuilder();
        rossinaIngredients.forEach(i -> b2.addIngredient(i.getLeft(), i.getRight()));
        b2.setWaterMeasurement(WaterMeasurementFactory.create(Arrays.asList(Pair.of(WaterMeasurement.Element.CALCIUM, new ParameterImpl(ParameterTypeEnum.WATER_MEASUREMENT, 1))))
                                                            .getValue());

        batchRossina = b2.build(
            rossina,
            BatchMethod.PARTIAL_MASH,
            Q1,
            StepTypeEnum.MASHING
        ).getValue();

        final BatchBuilder b3 = brewery.getBatchBuilder();
        biondinaIngredients.forEach(i -> b3.addIngredient(i.getLeft(), i.getRight()));

        batchBiondina = b3.build(
            biondina,
            BatchMethod.EXTRACT,
            Q2,
            StepTypeEnum.MASHING
        ).getValue();
    }

    /**
     * Method that tests equality between batches.
     */
    @Test
    public void testDifferentIds() {
        Assert.assertNotEquals(batchAlfredo.getId(), batchBiondina.getId());
        Assert.assertNotEquals(batchAlfredo.getId(), batchRossina.getId());
        Assert.assertNotEquals(batchBiondina.getId(), batchRossina.getId());

        Assert.assertNotEquals(batchAlfredo, batchBiondina);
        Assert.assertNotEquals(batchAlfredo, batchRossina);
        Assert.assertNotEquals(batchBiondina, batchRossina);
    }

    /**
     * Method that tests passages from one step to another.
     */
    @Test
    public void testChangeStep() {
        //Without finalizing.
        Result<Empty> res = batchAlfredo.moveToNextStep(StepTypeEnum.BOILING);
        Assert.assertTrue(res.isPresent());

        Assert.assertEquals(StepTypeEnum.BOILING, batchAlfredo.getCurrentStep().getStepInfo().getType());
        Assert.assertEquals(Q1, batchAlfredo.getBatchInfo().getBatchSize());

        Assert.assertEquals(2, batchAlfredo.getSteps().size());
        Step prevStep = batchAlfredo.getSteps().get(0);

        Assert.assertTrue(prevStep.isFinalized());
        Assert.assertFalse(prevStep.getStepInfo().getNote().isPresent());
        Assert.assertEquals(StepTypeEnum.MASHING, prevStep.getStepInfo().getType());
        Assert.assertTrue(prevStep.getStepInfo().getEndDate().isPresent());
        Assert.assertTrue(prevStep.getStepInfo().getEndStepSize().isPresent());
        Assert.assertEquals(Q1, prevStep.getStepInfo().getEndStepSize().get());

        //With finalizing.
        String notes = "Dummy notes.";
        Date endDate = new Date();

        batchRossina.getCurrentStep().finalize(notes, endDate, Q2);
        res = batchRossina.moveToNextStep(StepTypeEnum.BOILING);
        Assert.assertTrue(res.isPresent());

        Assert.assertNotEquals(Q2, batchRossina.getBatchInfo().getBatchSize());
        Assert.assertEquals(Q2, batchRossina.getCurrentSize());
        Assert.assertEquals(2, batchRossina.getSteps().size());
        prevStep = batchRossina.getSteps().get(0);

        Assert.assertTrue(prevStep.isFinalized());
        Assert.assertTrue(prevStep.getStepInfo().getNote().isPresent());
        Assert.assertEquals(notes, prevStep.getStepInfo().getNote().get());
        Assert.assertTrue(prevStep.getStepInfo().getEndDate().isPresent());
        Assert.assertEquals(endDate, prevStep.getStepInfo().getEndDate().get());
        Assert.assertEquals(Q2, prevStep.getStepInfo().getEndStepSize().get());

        //Wrong step change.
        res = batchBiondina.moveToNextStep(StepTypeEnum.FINALIZED);
        Assert.assertTrue(res.isError());
    }

    /**
     * Method that tests a complete batch production.
     */
    @Test
    public void testCompleteStepChanges() {
        Assert.assertFalse(batchAlfredo.isEnded());

        int nSteps = 1;

        //Example of production phase.
        //Check no last steps.
        Assert.assertEquals(nSteps, batchAlfredo.getSteps().size());

        final Number t1 = 20.1;
        final Number t2 = 18.9;

        //Register a bunch of temperatures.
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, t1));
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, t2));

        //Finalize and go next.
        batchAlfredo.getCurrentStep().finalize("Mashing ended.", new Date(), batchAlfredo.getBatchInfo().getBatchSize());
        batchAlfredo.moveToNextStep(StepTypeEnum.BOILING).peekError(e -> Assert.fail(e.getMessage()));

        Assert.assertEquals(++nSteps, batchAlfredo.getSteps().size());

        final Number t3 = 120.9;
        final Number t4 = 106.3;

        //Register other temps.
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, t3));
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, t4));

        //Finalize and go next.
        batchAlfredo.getCurrentStep().finalize("Boiling ended.", new Date(), batchAlfredo.getBatchInfo().getBatchSize());
        batchAlfredo.moveToNextStep(StepTypeEnum.FERMENTING).peekError(e -> Assert.fail(e.getMessage()));

        Assert.assertEquals(++nSteps, batchAlfredo.getSteps().size());

        final Number t5 = 50;
        final Number t6 = 45.8;

        //Register other temps and ABV.
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, t5));
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.TEMPERATURE, t6));

        final Number abv = 13;
        final Date d = new Date();
        Assert.assertFalse(batchAlfredo.getBatchInfo().getAbv().isPresent());
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.ABV, abv, d));
        //Check update on batchInfo
        Assert.assertTrue(batchAlfredo.getBatchInfo().getAbv().isPresent());
        Assert.assertEquals(new ParameterImpl(ParameterTypeEnum.ABV, abv, d), batchAlfredo.getBatchInfo().getAbv().get());

        final Number abv2 = 15;
        batchAlfredo.getCurrentStep().addParameter(new ParameterImpl(ParameterTypeEnum.ABV, abv2, d));
        Assert.assertTrue(batchAlfredo.getBatchInfo().getAbv().isPresent());
        Assert.assertEquals(new ParameterImpl(ParameterTypeEnum.ABV, abv2, d), batchAlfredo.getBatchInfo().getAbv().get());

        //Go next without finalize
        batchAlfredo.moveToNextStep(StepTypeEnum.PACKAGING).peekError(e -> Assert.fail(e.getMessage()));

        Assert.assertEquals(++nSteps, batchAlfredo.getSteps().size());

        final int bottles = 7;
        //Finalize packaging with bottle um.
        batchAlfredo.getCurrentStep().finalize("Packaged in 75 cl bottles", new Date(), Quantity.of(bottles, UnitOfMeasure.BOTTLE_75_CL).getValue());

        batchAlfredo.moveToNextStep(StepTypeEnum.FINALIZED).peekError(e -> Assert.fail(e.getMessage()));

        //Check current batch size.
        Assert.assertEquals(Quantity.of(bottles, UnitOfMeasure.BOTTLE_75_CL).getValue(), batchAlfredo.getCurrentSize());

        //Check end.
        Assert.assertTrue(batchAlfredo.isEnded());

        final int flavor = 9;
        final int aroma = 9;
        final int appearance = 1;
        final int mouthfeel = 4;
        final int overrallImpression = 9;

        //Insert review.
        Set<Evaluation> evals = Stream.<Result<Evaluation>>builder()
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.AROMA, aroma))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, appearance))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.FLAVOR, flavor))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, mouthfeel))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, overrallImpression))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());

        batchAlfredo.setEvaluation(new BatchEvaluationBuilder()
            .build(bjcpType, evals).getValue());

        //Check all steps are registered.
        Assert.assertEquals(++nSteps, batchAlfredo.getSteps().size());

        //Stock this batch.
        batchAlfredo.moveToNextStep(StepTypeEnum.STOCKED).peekError(e -> Assert.fail(e.getMessage()));
        Assert.assertEquals(++nSteps, batchAlfredo.getSteps().size());

        //Go to wrong step type.
        batchAlfredo.moveToNextStep(StepTypeEnum.MASHING).peek(e -> Assert.fail());
        Assert.assertNotEquals(++nSteps, batchAlfredo.getSteps().size());
    }

    /**
     * Method that tests end step sizes correctness.
     */
    @Test
    public void testEndSizeSteps() {
        batchBiondina.moveToNextStep(StepTypeEnum.BOILING).peekError(e -> Assert.fail(e.getMessage()));

        //Check same size as started
        Assert.assertEquals(batchBiondina.getBatchInfo().getBatchSize(),
            batchBiondina.getSteps().get(0).getStepInfo().getEndStepSize().get());

        final int evapo = 1000;
        batchBiondina.getCurrentStep().finalize("Evaporated",
            new Date(),
            Quantities.remove(Q2, Quantity.of(evapo, UnitOfMeasure.MILLILITER).getValue()).getValue()
        );

        batchBiondina.moveToNextStep(StepTypeEnum.FERMENTING).peekError(e -> Assert.fail(e.getMessage()));

        //Check changed.
        Assert.assertNotEquals(batchBiondina.getSteps().get(1).getStepInfo().getEndStepSize().get(),
            batchBiondina.getBatchInfo().getBatchSize());
    }
}
