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
    private Collection<BatchReviewType> types;

    @Override
    public Result<Collection<BatchReviewType>> getAvailableBatchReviewTypes() {
        if (types != null) {
            return Result.of(types);
        } else {
            return this.scan()
                .peek(res -> this.types = res);
        }
    }


    private Result<Collection<BatchReviewType>> scan() {
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
