package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.WarehouseImpl;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Brewery Implementation.
 */
public final class BreweryImpl implements Brewery {

    private final String breweryName;
    private final String ownerName;
    private final Warehouse warehouse = new WarehouseImpl();
    private final Collection<Batch> batches = new ArrayList<>();

    /**
     * Constructor of an Implementation of the {@link Brewery}.
     * @param breweryName the name of the {@link Brewery}.
     * @param ownerName the name of the owner.
     */
    public BreweryImpl(final String breweryName, final String ownerName) {
        this.breweryName = breweryName;
        this.ownerName = ownerName;
    }

    @Override
    public String getBreweryName() {
        return breweryName;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public Warehouse getWarehouse() {
        return warehouse;
    }

    @Override
    public Collection<Batch> getBatches(final QueryBatch queryBatch) {
        final Collection<Batch> retBatches = new ArrayList<>(batches);
        return retBatches.stream()
                         .filter(batch -> !(queryBatch.getMinId().isPresent()
                             && batch.getId() < queryBatch.getMinId().get()))
                         .filter(batch -> !(queryBatch.getMaxId().isPresent()
                              && batch.getId() > queryBatch.getMaxId().get()))
                         .filter(batch -> !(queryBatch.getBatchMethod().isPresent()
                                         && batch.getBatchInfo().getMethod()
                                         != queryBatch.getBatchMethod().get()))
                         .filter(batch -> !(queryBatch.getMinBatchSize().isPresent()
                              && batch.getBatchInfo()
                                      .getBatchSize()
                                      .lessThan(queryBatch.getMinBatchSize().get())))
                         .filter(batch -> !(queryBatch.getMaxBatchSize().isPresent()
                              && batch.getBatchInfo()
                                      .getBatchSize()
                                      .moreThan(queryBatch.getMaxBatchSize().get())))
                         .collect(Collectors.toList());
    }

    @Override
    public void addBatch(final Batch newBatch) {
        batches.add(newBatch);
    }

    @Override
    public Result<Empty> stockBatch(final Batch batch, final BeerArticle beerArticle, @Nullable final Date expirationDate) {
        return Result.of(batch)
                     .require(batch::isEnded)
                     .peek(b -> b.moveToNextStep(StepTypeEnum.STOCKED))
                     //.map(Batch::getCurrentQuantity)
                     //.require(q -> q.getUnitOfMeasure().equals(beerArticle.getUnitOfMeasure()))
                     //.map(b -> batch)
                     .flatMap(b -> warehouse.createBeerStock(beerArticle, expirationDate, batch))
                     //.peek(beerStock -> beerStock.addRecord(new Record(batch.getCurrentQuantity(), Record.Action.ADDING)))
                     .toEmpty();
    }
}
