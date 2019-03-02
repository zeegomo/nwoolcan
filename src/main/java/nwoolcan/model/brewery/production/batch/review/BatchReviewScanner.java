package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.utils.Result;

import java.util.Collection;

/**
 * Scan the codebase to find all implementations of BatchReviewImpl.
 */
public interface BatchReviewScanner {

    /**
     * Return all BatchReviewType found by the scanner.
     * @return a {@link Collection} of {@link BatchReviewType}
     */
    Result<Collection<BatchReviewType>> getAvailableBatchReviewTypes();
}
