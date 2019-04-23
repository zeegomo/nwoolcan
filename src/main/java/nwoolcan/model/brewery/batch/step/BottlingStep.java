package nwoolcan.model.brewery.batch.step;

import nwoolcan.model.utils.Quantity;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Step that can be finalized only if the remaining size has a bottle unit of measure.
 */
final class BottlingStep extends DecoratorStep {

    private static final String CANNOT_FINALIZE_WITH_NO_BOTTLE_MESSAGE = "Cannot finalize step because remaining size has no bottle unit of measure.";

    private static final Set<UnitOfMeasure> BOTTLES = new HashSet<>(Arrays.asList(
        UnitOfMeasure.BOTTLE_33_CL,
        UnitOfMeasure.BOTTLE_50_CL,
        UnitOfMeasure.BOTTLE_66_CL,
        UnitOfMeasure.BOTTLE_75_CL,
        UnitOfMeasure.BOTTLE_MAGNUM
    ));

    /**
     * Basic decorator constructor.
     * Package-private.
     * @param decorated the decorated step implementation.
     */
    BottlingStep(final AbstractStep decorated) {
        super(decorated);
    }

    @Override
    protected Result<Empty> checkFinalizationData(@Nullable final String note, final Date endDate, final Quantity remainingSize) {
        return super.checkFinalizationData(note, endDate, remainingSize)
                    .require(s -> BOTTLES.contains(remainingSize.getUnitOfMeasure()),
                        new IllegalArgumentException(CANNOT_FINALIZE_WITH_NO_BOTTLE_MESSAGE))
                    .toEmpty();
    }
}
