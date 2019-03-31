package nwoolcan.model.brewery.production.batch.review.types;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import nwoolcan.model.brewery.production.batch.review.BatchEvaluationType;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Implementation for BatchEvaluationTypeScanner.
 */
public final class BatchEvaluationScannerImpl implements BatchEvaluationTypeScanner {
    @Nullable
    private Set<BatchEvaluationType> types;

    @Override
    public Result<Set<BatchEvaluationType>> getAvailableBatchEvaluationTypes() {
        if (types != null) {
            return Result.of(types);
        } else {
            return this.scan()
                .peek(res -> this.types = res);
        }
    }

    private Result<Set<BatchEvaluationType>> scan() {
        return Results.ofCloseable(() ->  new ClassGraph().enableAllInfo().scan(), scanResult -> {
            ClassInfoList widgetClasses = scanResult.getClassesImplementing(BatchEvaluationType.class.getName());
            return widgetClasses
                .loadClasses(BatchEvaluationType.class)
                .stream()
                .flatMap(review -> {
                    if (review.isEnum()) {
                        return Arrays.stream(review.getEnumConstants()).map(Result::of);
                    } else {
                        return Results.ofChecked(review::newInstance)
                                      .peekError(error -> Logger.getGlobal().warning(error.getMessage()))
                                      .stream();
                    }
                })
                .filter(Result::isPresent)
                .map(Result::getValue)
                .collect(Collectors.toSet());
        });
    }
}
