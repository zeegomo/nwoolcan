package nwoolcan.model.brewery;

import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.BatchBuilder;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.WarehouseImpl;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
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

    private static final String CANNOT_STOCK_NOT_ENDED_BATCH_MESSGE = "Cannot stock a non ended batch.";
    private static final String ALREADY_STOCKED_BATCH_MESSAGE = "The batch is already stocked.";
    private static final String WRONG_ARTICLE_UNIT_OF_MEASURE_MESSAGE = "Article unit of measure and batch current size unit of measure do not match.";

    @Nullable private String breweryName;
    @Nullable private String ownerName;
    private final Warehouse warehouse = new WarehouseImpl();
    private final Collection<Batch> batches = new ArrayList<>();
    private final IdGenerator batchIdGenerator = new BatchIdGenerator(0);

    @Override
    public synchronized Optional<String> getBreweryName() {
        return Optional.ofNullable(breweryName);
    }

    @Override
    public synchronized Optional<String> getOwnerName() {
        return Optional.ofNullable(ownerName);
    }

    @Override
    public synchronized Warehouse getWarehouse() {
        return warehouse;
    }

    @Override
    public synchronized Collection<Batch> getBatches(final QueryBatch queryBatch) {
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
    public synchronized void addBatch(final Batch newBatch) {
        batches.add(newBatch);
    }

    @Override
    public BatchBuilder getBatchBuilder() {
        return new BatchBuilder(this.batchIdGenerator);
    }

    @Override
    public synchronized Result<Empty> stockBatch(final Batch batch, final BeerArticle beerArticle, @Nullable final Date expirationDate) {
        return Result.of(batch)
                     .flatMap(b -> b.stockBatchInto(beerArticle, () -> {
                         BeerStock newStock;
                         if (expirationDate == null) {
                             newStock = this.warehouse.createBeerStock(beerArticle, batch).getValue();
                         } else {
                             newStock = this.warehouse.createBeerStock(beerArticle, expirationDate, batch).getValue();
                         }
                         return newStock;
                     }))
                     .toEmpty();
    }
    @Override
    public synchronized void setBreweryName(final String breweryName) {
        this.breweryName = breweryName;
    }
    @Override
    public synchronized void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }
    @Override
    public synchronized Result<Empty> stockBatch(final Batch batch, final BeerArticle beerArticle) {
        return stockBatch(batch, beerArticle, null);
    }

    private synchronized Result<BeerStock> createBeerStock(final BeerArticle beerArticle,
                                              @Nullable final Date expirationDate,
                                              final Batch batch) {
        if (expirationDate == null) {
            return warehouse.createBeerStock(beerArticle, batch);
        }
        return warehouse.createBeerStock(beerArticle, expirationDate, batch);
    }

}
