package nwoolcan.model.brewery.production.batch.review;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of BatchReviewScanner.
 */
public class BatchReviewScannerImpl implements BatchReviewScanner {

    @Override
    public final Result<Collection<BatchReviewType>> getAvailableBatchReviewTypes() {
        return Results.ofCloseable(() ->  new ClassGraph().enableAllInfo().scan(), scanResult -> {
            ClassInfoList widgetClasses = scanResult.getClassesImplementing(BatchReviewType.class.getName());
            return widgetClasses
                .loadClasses(BatchReviewType.class)
                .stream()
                .flatMap(review -> {
                    if (review.isEnum()) {
                        return Arrays.stream(review.getEnumConstants()).map(Result::of);
                    } else {
                        return Stream.of(Results.ofChecked(review::newInstance));
                    }
                })
                .filter(Result::isPresent)
                .map(Result::getValue)
                .collect(Collectors.toList());
        });
    }
}
