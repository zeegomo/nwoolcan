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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Brewery Implementation.
 */
public final class BreweryImpl implements Brewery {

    @Nullable private String breweryName;
    @Nullable private String ownerName;
    private final Warehouse warehouse = new WarehouseImpl();
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
        Stream<Batch> s = this.batches.stream();
        final List<Predicate<Batch>> filters = new ArrayList<>();

        queryBatch.getBatchId().ifPresent(id -> filters.add(b -> b.getId() == id));
        queryBatch.getBeerName().ifPresent(name -> filters.add(b -> b.getBatchInfo()
                                                                     .getBeerDescription()
                                                                     .getName()
                                                                     .toLowerCase()
                                                                     .contains(name.toLowerCase())));
        queryBatch.getBeerStyle().ifPresent(style -> filters.add(b -> b.getBatchInfo()
                                                                       .getBeerDescription()
                                                                       .getStyle()
                                                                       .toLowerCase()
                                                                       .contains(style.toLowerCase())));
        queryBatch.getBatchMethod().ifPresent(method -> filters.add(b -> b.getBatchInfo().getMethod().equals(method)));
        queryBatch.getMinBatchSize().ifPresent(minSize -> filters.add(b -> b.getBatchInfo()
                                                                            .getInitialBatchSize()
                                                                            .moreThan(minSize)));
        queryBatch.getMaxBatchSize().ifPresent(maxSize -> filters.add(b -> b.getBatchInfo()
                                                                            .getInitialBatchSize()
                                                                            .lessThan(maxSize)));

        for (final Predicate<Batch> f : filters) {
            s = s.filter(f);
        }

        return s.collect(Collectors.toList());
    }

    @Override
    public Collection<Batch> getBatches() {
        return Collections.unmodifiableCollection(this.batches);
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
