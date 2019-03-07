package nwoolcan.model.brewery.production.batch.review;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import nwoolcan.utils.Result;
import nwoolcan.utils.Results;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation for BatchEvaluationScanner.
 */
public final class BatchEvaluationScannerImpl implements BatchEvaluationScanner {
    private Set<BatchEvaluationType> types;

    //Package private
    BatchEvaluationScannerImpl() { }

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
                        return Stream.of(Results.ofChecked(review::newInstance));
                    }
                })
                .filter(Result::isPresent)
                .map(Result::getValue)
                .collect(Collectors.toSet());
        });
    }
}
