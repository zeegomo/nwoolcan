package nwoolcan.model.brewery;

import nwoolcan.model.brewery.production.batch.Batch;
import nwoolcan.model.brewery.production.batch.QueryBatch;
import nwoolcan.model.brewery.production.batch.step.StepTypeEnum;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.WarehouseImpl;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.Record;
import nwoolcan.utils.Empty;
import nwoolcan.utils.Result;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Brewery Implementation.
 */
public final class BreweryImpl implements Brewery {

    @Nullable private static BreweryImpl instance;
    @Nullable private String breweryName;
    @Nullable private String ownerName;
    private final Warehouse warehouse = new WarehouseImpl();
    private final Collection<Batch> batches = new ArrayList<>();

    private BreweryImpl() { }

    /**
     * Return the only instance of the {@link Brewery}.
     * @return the only instance of the {@link Brewery}.
     */
    public static synchronized Brewery getInstance() {
        if (instance == null) {
            instance = new BreweryImpl();
        }
        return instance;
    }

    @Override
    public Optional<String> getBreweryName() {
        return Optional.ofNullable(breweryName);
    }

    @Override
    public Optional<String> getOwnerName() {
        return Optional.ofNullable(ownerName);
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
                     .map(Batch::getCurrentSize)
                     .require(q -> q.getUnitOfMeasure().equals(beerArticle.getUnitOfMeasure()))
                     .map(quantity -> batch)
                     .flatMap(b -> createBeerStock(beerArticle, expirationDate, batch))
                     .peek(beerStock -> beerStock.addRecord(new Record(batch.getCurrentSize(), Record.Action.ADDING)))
                     .toEmpty();
    }
    @Override
    public void setBreweryName(final String breweryName) {
        this.breweryName = breweryName;
    }
    @Override
    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }
    @Override
    public Result<Empty> stockBatch(final Batch batch, final BeerArticle beerArticle) {
        return stockBatch(batch, beerArticle, null);
    }

    private Result<BeerStock> createBeerStock(final BeerArticle beerArticle,
                                              @Nullable final Date expirationDate,
                                              final Batch batch) {
        if (expirationDate == null) {
            return warehouse.createBeerStock(beerArticle, batch);
        }
        return warehouse.createBeerStock(beerArticle, expirationDate, batch);
    }

}
