package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType.BJCPCategories;
import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType;
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
                                      .add(EvaluationImpl.create(BJCPCategories.AROMA, 10))
                                      .add(EvaluationImpl.create(BJCPCategories.APPEARANCE, 3))
                                      .add(EvaluationImpl.create(BJCPCategories.FLAVOR, BJCPCategories.FLAVOR.getMaxScore()))
                                      .add(EvaluationImpl.create(BJCPCategories.MOUTHFEEL, 4))
                                      .add(EvaluationImpl.create(BJCPCategories.OVERALL_IMPRESSION, 10))
                                      .build()
                                      .filter(Result::isPresent)
                                      .map(Result::getValue)
                                      .collect(Collectors.toSet());


        BatchEvaluationBuilder builder = new BatchEvaluationBuilder(bjcpType, evals);

        Result<BatchEvaluation> bjcp = builder.addReviewer("Andrea")
                                              .addNotes("Very good")
                                              .build();

        assertTrue(bjcp.isPresent());
        BatchEvaluation review = bjcp.getValue();
        assertEquals(review.getReviewer(), Optional.of("Andrea"));
        assertEquals(review.getNotes(), Optional.of("Very good"));
        assertEquals(review.getScore(), expectedValue);
        assertEquals(review.getEvaluationType(), bjcpType);
        assertTrue(builder.build().isError());
        assertEquals(review.getCategoryEvaluations()
                           .stream()
                           .filter(e -> e.getEvaluationType().equals(BJCPCategories.APPEARANCE))
                           .findAny()
                           .get()
                           .getScore(), 3);
    }

    /**
     * Test builder.
     */
    @Test
    public void testAvailableEvaluationTypes() {
        final int expectedSize = 2;

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
            .add(EvaluationImpl.create(BJCPCategories.AROMA, 10))
            .add(EvaluationImpl.create(BJCPCategories.APPEARANCE, 10))
            .add(EvaluationImpl.create(BJCPCategories.FLAVOR, BJCPCategories.FLAVOR.getMaxScore()))
            .add(EvaluationImpl.create(BJCPCategories.MOUTHFEEL, 4))
            .add(EvaluationImpl.create(BJCPCategories.OVERALL_IMPRESSION, 10))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());

        BatchEvaluationBuilder builder = new BatchEvaluationBuilder(bjcpType, evals);

        Result<BatchEvaluation> test1 = builder.addReviewer("Andrea")
                                               .build();

        assertTrue(test1.isError());

        Set<Evaluation> evals1 = Stream.<Result<Evaluation>>builder()
            .add(EvaluationImpl.create(BJCPCategories.AROMA, 10))
            .add(EvaluationImpl.create(BJCPCategories.APPEARANCE, 10))
            .add(EvaluationImpl.create(BJCPCategories.FLAVOR, BJCPCategories.FLAVOR.getMaxScore()))
            .add(EvaluationImpl.create(BJCPCategories.MOUTHFEEL, 4))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());

        Result<BatchEvaluation> test2 = builder.reset(bjcpType, evals)
                                               .addReviewer("Andrea")
                                               .build();

        assertTrue(test2.isError());

        Set<Evaluation> evals2 = Stream.<Result<Evaluation>>builder()
            .add(EvaluationImpl.create(BJCPCategories.AROMA, 10))
            .add(EvaluationImpl.create(BJCPCategories.APPEARANCE, -1))
            .add(EvaluationImpl.create(BJCPCategories.FLAVOR, BJCPCategories.FLAVOR.getMaxScore()))
            .add(EvaluationImpl.create(BJCPCategories.MOUTHFEEL, 4))
            .add(EvaluationImpl.create(BJCPCategories.OVERALL_IMPRESSION, 10))
            .build()
            .filter(Result::isPresent)
            .map(Result::getValue)
            .collect(Collectors.toSet());


        Result<BatchEvaluation> test3 = builder.reset(bjcpType, evals)
                                               .addReviewer("Andrea")
                                               .build();

        assertTrue(test3.isError());
    }
}
