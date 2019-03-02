package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.model.brewery.production.batch.review.types.BJCPBatchReviewType;
import nwoolcan.utils.Result;
import org.junit.Test;


import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test BatchReview.
 */
public class TestBatchReview {
    /**
     * Test builder.
     */
    @Test
    public void testSuccessfulBuilder() {
        final int expectedValue = 47;
        BatchReviewBuilder builder = new BatchReviewBuilder(new BJCPBatchReviewType());

        Result<BatchReview> bjcp = builder
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.AROMA, 10)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.APPEARANCE, 3)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.FLAVOR,
                BJCPBatchReviewType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .addNotes("Very good")
            .build();

        assertTrue(bjcp.isPresent());
        BatchReview review = bjcp.getValue();
        assertEquals(review.getReviewer(), Optional.of("Andrea"));
        assertEquals(review.getNotes(), Optional.of("Very good"));
        assertEquals(review.getScore(), expectedValue);
        assertEquals(review.getEvaluationType(), new BJCPBatchReviewType());
        assertTrue(builder.build().isError());
    }

    /**
     * Test builder.
     */
    @Test
    public void testAvailableEvaluationTypes() {
        final int expectedSize = 2;
        BatchReviewBuilder builder = new BatchReviewBuilder(new BJCPBatchReviewType());
        Result<Collection<BatchReviewType>> available = BatchReviewBuilder.getAvailableBatchReviewTypes();
        assertTrue(available.isPresent());
        Collection<BatchReviewType> types = available.getValue();
        System.out.println(types);
        assertEquals(types.size(), expectedSize);
        assertTrue(types.contains(new BJCPBatchReviewType()));
        System.out.println(types);
    }

    /**
     * Test builder.
     */
    @Test
    public void testFailedBuilder() {
        BatchReviewBuilder builder = new BatchReviewBuilder(new BJCPBatchReviewType());

        Result<BatchReview> test1 = builder
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.AROMA, 10)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.APPEARANCE, 10)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.FLAVOR,
                BJCPBatchReviewType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .build();

        assertTrue(test1.isError());

        Result<BatchReview> test2 = builder
            .reset(new BJCPBatchReviewType())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.AROMA,
                BJCPBatchReviewType.BJCPCategories.AROMA.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.APPEARANCE, 3)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.FLAVOR,
                BJCPBatchReviewType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.MOUTHFEEL, 4)
            .addReviewer("Andrea")
            .build();

        assertTrue(test2.isError());

        Result<BatchReview> test3 = builder
            .reset(new BJCPBatchReviewType())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.AROMA,
                BJCPBatchReviewType.BJCPCategories.AROMA.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.APPEARANCE, -1)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.FLAVOR,
                BJCPBatchReviewType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .build();

        assertTrue(test3.isError());

        Result<BatchReview> test4 = builder
            .reset(new BJCPBatchReviewType())
            .addEvaluation(null,
                BJCPBatchReviewType.BJCPCategories.AROMA.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.APPEARANCE, 1)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.FLAVOR,
                BJCPBatchReviewType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .build();

        assertTrue(test4.isError());
    }
}
