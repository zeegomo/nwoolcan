package nwoolcan.model.brewery.production.batch;

import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import nwoolcan.utils.Pair;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

public class BatchInfoImpl implements BatchInfo {
    private final BeerDescription beerDescription;
    private final BatchMethod method;
    private final Quantity size;
    @Nullable
    private final WaterMeasurement measurement;
    @Nullable
    private Quantity og;
    @Nullable
    private Quantity fg;
    @Nullable
    private Quantity srm;
    @Nullable
    private Quantity abv;
    @Nullable
    private Quantity ibu;

    public BatchInfoImpl(final BeerDescription beerDescription, final BatchMethod method, final Quantity size, final WaterMeasurement measurement) {
        this.beerDescription = beerDescription;
        this.method = method;
        this.size = size;
        this.measurement = measurement;
    }

    @Override
    public BeerDescription getBeerDescription() {
        return this.beerDescription;
    }

    @Override
    public BatchMethod getMethod() {
        return this.method;
    }

    @Override
    public Quantity getBatchSize() {
        return this.size;
    }

    @Override
    public Optional<Quantity> getOg() {
        return Optional.ofNullable(this.og);
    }

    @Override
    public Optional<Quantity> getFg() {
        return Optional.ofNullable(this.fg);
    }

    @Override
    public Optional<Quantity> getSrm() {
        return Optional.ofNullable(this.srm);
    }

    @Override
    public Optional<Quantity> getAbv() {
        return Optional.ofNullable(this.abv);
    }

    @Override
    public Optional<Quantity> getIbu() {
        return Optional.ofNullable(this.ibu);
    }

    @Override
    public Optional<WaterMeasurement> getWaterMeasurements() {
        return Optional.ofNullable(this.measurement);
    }

    @Override
    public Collection<Pair<IngredientArticle, Quantity>> listIngredients() {
        return null;
    }
}
