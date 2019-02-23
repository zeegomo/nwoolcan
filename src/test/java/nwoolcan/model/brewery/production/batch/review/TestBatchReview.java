package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.utils.Result;
import org.junit.Test;

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
            .reset()
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
            .reset()
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.AROMA,
                BJCPBatchReviewType.BJCPCategories.AROMA.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.APPEARANCE, -3)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.FLAVOR,
                BJCPBatchReviewType.BJCPCategories.FLAVOR.getMaxScore())
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.MOUTHFEEL, 4)
            .addEvaluation(BJCPBatchReviewType.BJCPCategories.OVERALL_IMPRESSION, 10)
            .addReviewer("Andrea")
            .build();

        assertTrue(test3.isError());
    }
}
