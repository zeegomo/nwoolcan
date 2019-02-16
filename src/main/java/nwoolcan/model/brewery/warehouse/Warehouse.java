package nwoolcan.model.brewery.warehouse;

import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import java.util.Collection;

/**
 * Warehouse.
 */
public interface Warehouse {
    /**
     * Getter of all the Udc in the warehouse.
     * @param queryUdc describes the nature of the query.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Collection<Udc>> getUdcs(QueryUdc queryUdc);

    /**
     * Adds a Udc to the warehouse.
     * @param newUdc the new uds to be added.
     * @return a {@link Result} of {@link Empty} which reports possible errors.
     */
    Result<Empty> addUdc(Udc newUdc);
}
