package nwoolcan.model.brewery;

import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.batch.BatchBuilder;
import nwoolcan.model.brewery.batch.QueryBatch;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.WarehouseImpl;
import nwoolcan.model.brewery.warehouse.article.ArticleManager;
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

    @Nullable private String breweryName;
    @Nullable private String ownerName;
    private final ArticleManager articleManager = new ArticleManager();
    private final Warehouse warehouse = new WarehouseImpl(articleManager);
    private final Collection<Batch> batches = new ArrayList<>();
    private final IdGenerator batchIdGenerator = new BatchIdGenerator(0);
    private static final String BATCH_NOT_FOUND = "Batch not found.";

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
    public Collection<Batch> getBatches() {
        return new ArrayList<>(batches);
    }

    @Override
    public Result<Batch> getBatchById(final int batchId) {
        return Result.of(batches.stream().filter(batch -> batch.getId() == batchId).findFirst())
                     .require(Optional::isPresent,
                         new IllegalArgumentException(BATCH_NOT_FOUND))
                     .map(Optional::get);
    }

    @Override
    public void addBatch(final Batch newBatch) {
        batches.add(newBatch);
    }

    @Override
    public BatchBuilder getBatchBuilder() {
        return new BatchBuilder(this.batchIdGenerator);
    }

    @Override
    public Result<Empty> stockBatch(final Batch batch, final BeerArticle beerArticle, @Nullable final Date expirationDate) {
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
