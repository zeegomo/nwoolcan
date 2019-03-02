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
 * Implementation for BatchReviewScanner.
 */
public final class BatchReviewScannerImpl implements BatchReviewScanner {

    @Override
    public Result<Collection<BatchReviewType>> getAvailableBatchReviewTypes() {
        return Results.ofCloseable(() ->  new ClassGraph().enableAllInfo().scan(), scanResult -> {
            ClassInfoList widgetClasses = scanResult.getClassesImplementing(BatchReviewType.class.getName());
            System.out.println(widgetClasses.size());
            return widgetClasses
                .loadClasses(BatchReviewType.class)
                .stream()
                .peek(e -> System.out.println(e.toString()))
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
