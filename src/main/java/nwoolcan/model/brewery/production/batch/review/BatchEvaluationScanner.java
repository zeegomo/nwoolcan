package nwoolcan.model.brewery.production.batch.review;

import nwoolcan.utils.Result;

import java.util.Collection;

/**
 * Scan the codebase to find all implementations of BatchEvaluationImpl.
 */
public interface BatchEvaluationScanner {

    /**
     * Return all BatchEvaluationType found by the scanner.
     * @return a {@link Collection} of {@link BatchEvaluationType}
     */
    Result<Collection<BatchEvaluationType>> getAvailableBatchReviewTypes();
}
