package nwoolcan.model.brewery.batch.review;

import nwoolcan.model.brewery.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.utils.Result;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test BatchEvaluation.
 */
public class TestBatchEvaluation {

    private final BatchEvaluationType bjcpType = BatchEvaluationBuilder.getAvailableBatchEvaluationTypes()
                                                         .getValue()
                                                         .stream()
                                                         .filter(s -> s.getClass().equals(BJCPBatchEvaluationType.class))
                                                         .findAny().get();

    /**
     * Test builder.
     */
    @Test
    public void testSuccessfulBuilder() {
        final int expectedValue = 47;

        Set<Evaluation> evals = Stream.<Result<Evaluation>>builder()
                                      .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10))
                                      .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 3))
                                      .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.FLAVOR, BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore()))
                                      .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4))
                                      .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10))
                                      .build()
                                      .filter(Result::isPresent)
                                      .map(Result::getValue)
                                      .collect(Collectors.toSet());


        BatchEvaluationBuilder builder = new BatchEvaluationBuilder();

        Result<BatchEvaluation> bjcp = builder.addReviewer("Andrea")
                                              .addNotes("Very good")
                                              .build(bjcpType, evals);

        assertTrue(bjcp.isPresent());
        BatchEvaluation review = bjcp.getValue();
        assertEquals(review.getReviewer(), Optional.of("Andrea"));
        assertEquals(review.getNotes(), Optional.of("Very good"));
        assertEquals(review.getScore(), expectedValue);
        assertEquals(review.getEvaluationType(), bjcpType);
        assertEquals(review.getCategoryEvaluations()
                           .stream()
                           .filter(e -> e.getEvaluationType().equals(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE))
                           .findAny()
                           .get()
                           .getScore(), 3);

        Result<BatchEvaluation> bjcp2 = builder.addReviewer("Mirko")
                                              .addNotes("Code Smell")
                                              .build(bjcpType, evals);
        assertTrue(bjcp2.isPresent());
    }

    /**
     * Test builder.
     */
    @Test
    public void testAvailableEvaluationTypes() {
        final int expectedSize = 3;

        Result<Set<BatchEvaluationType>> available = BatchEvaluationBuilder.getAvailableBatchEvaluationTypes();
        assertTrue(available.isPresent());
        Set<BatchEvaluationType> types = available.getValue();
        System.out.println(types);
        assertEquals(types.size(), expectedSize);
        assertTrue(types.contains(bjcpType));
        System.out.println(types);
    }

    /**
     * Test builder.
     */
    @Test
    public void testFailedBuilder() {

        Set<Evaluation> evals = Stream.<Result<Evaluation>>builder()
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 10))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.FLAVOR, BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore()))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());

        BatchEvaluationBuilder builder = new BatchEvaluationBuilder();

        Result<BatchEvaluation> test1 = builder.addReviewer("Andrea")
                                               .build(bjcpType, evals);

        assertTrue(test1.isError());

        Set<Evaluation> evals1 = Stream.<Result<Evaluation>>builder()
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 10))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.FLAVOR, BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore()))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());

        Result<BatchEvaluation> test2 = builder.addReviewer("Andrea")
                                               .build(bjcpType, evals1);

        assertTrue(test2.isError());

        Set<Evaluation> evals2 = Stream.<Result<Evaluation>>builder()
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, -1))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.FLAVOR, BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore()))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4))
            .add(EvaluationFactory.create(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());


        Result<BatchEvaluation> test3 = builder.addReviewer("Andrea")
                                               .build(bjcpType, evals2);

        assertTrue(test3.isError());
    }

    /**
     *
     */
    @Test
    public void testBatchEvaluation() {
        final int expected = 6;
        Set<Evaluation> evals = Stream.<Result<Evaluation>>builder()
            .add(EvaluationFactory.create(AverageEvaluation.DummyReviewCategories.CAT1, 10))
            .add(EvaluationFactory.create(AverageEvaluation.DummyReviewCategories.CAT2, 2))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());
        BatchEvaluationBuilder builder = new BatchEvaluationBuilder();
        Result<BatchEvaluation> eval = builder.build(new AverageEvaluation(), evals);
        assertTrue(eval.isPresent());
        assertEquals(eval.getValue().getScore(), expected);
    }
}
