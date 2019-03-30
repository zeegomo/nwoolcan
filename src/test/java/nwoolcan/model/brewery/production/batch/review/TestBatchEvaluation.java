package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchEvaluationType;
import nwoolcan.utils.Result;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

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
        BatchEvaluationBuilder builder = new BatchEvaluationBuilder(bjcpType);

        Result<BatchEvaluation> bjcp = builder
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 3)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.FLAVOR,
                BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .addNotes("Very good")
            .build();

        assertTrue(bjcp.isPresent());
        BatchEvaluation review = bjcp.getValue();
        assertEquals(review.getReviewer(), Optional.of("Andrea"));
        assertEquals(review.getNotes(), Optional.of("Very good"));
        assertEquals(review.getScore(), expectedValue);
        assertEquals(review.getEvaluationType(), bjcpType);
        assertTrue(builder.build().isError());
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
        BatchEvaluationBuilder builder = new BatchEvaluationBuilder(bjcpType);

        Result<BatchEvaluation> test1 = builder
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.AROMA, 10)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 10)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.FLAVOR,
                BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .build();

        assertTrue(test1.isError());

        Result<BatchEvaluation> test2 = builder
            .reset(bjcpType)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.AROMA,
                BJCPBatchEvaluationType.BJCPCategories.AROMA.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 3)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.FLAVOR,
                BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4)
            .addReviewer("Andrea")
            .build();

        assertTrue(test2.isError());

        Result<BatchEvaluation> test3 = builder
            .reset(bjcpType)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.AROMA,
                BJCPBatchEvaluationType.BJCPCategories.AROMA.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, -1)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.FLAVOR,
                BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .build();

        assertTrue(test3.isError());

        Result<BatchEvaluation> test4 = builder
            .reset(bjcpType)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.APPEARANCE, 1)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.FLAVOR,
                BJCPBatchEvaluationType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchEvaluationType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .build();

        assertTrue(test4.isError());
    }
}
