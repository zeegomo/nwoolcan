package nwoolcan.model.brewery.batch;

import nwoolcan.model.brewery.batch.misc.BeerDescription;
import nwoolcan.model.brewery.batch.misc.WaterMeasurement;
import nwoolcan.model.brewery.batch.step.parameter.Parameter;
import nwoolcan.model.brewery.batch.step.parameter.ParameterTypeEnum;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.utils.Quantity;
import org.apache.commons.lang3.tuple.Pair;


import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

//Package-protected
class ModifiableBatchInfoImpl implements ModifiableBatchInfo {
    private final BeerDescription beerDescription;
    private final BatchMethod method;
    private final Quantity size;
    private final Collection<Pair<IngredientArticle, Double>> ingredients;
    @Nullable
    private final WaterMeasurement measurement;
    @Nullable
    private Parameter og;
    @Nullable
    private Parameter fg;
    @Nullable
    private Parameter ebc;
    @Nullable
    private Parameter abv;
    @Nullable
    private Parameter ibu;

    //Package-private
    ModifiableBatchInfoImpl(final Collection<Pair<IngredientArticle, Double>> ingredients,
                            final BeerDescription beerDescription,
                            final BatchMethod method,
                            final Quantity size,
                            @Nullable final WaterMeasurement measurement) {
        this.ingredients = ingredients;
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
    public Optional<Parameter> getOg() {
        return Optional.ofNullable(this.og);
    }

    @Override
    public Optional<Parameter> getFg() {
        return Optional.ofNullable(this.fg);
    }

    @Override
    public Optional<Parameter> getEbc() {
        return Optional.ofNullable(this.ebc);
    }

    @Override
    public Optional<Parameter> getAbv() {
        return Optional.ofNullable(this.abv);
    }

    @Override
    public Optional<Parameter> getIbu() {
        return Optional.ofNullable(this.ibu);
    }

    @Override
    public Optional<WaterMeasurement> getWaterMeasurements() {
        return Optional.ofNullable(this.measurement);
    }

    @Override
    public Collection<Pair<IngredientArticle, Double>> listIngredients() {
        return this.ingredients;
    }

    @Override
    public void update(final Parameter p) {
        if (p.getType().equals(ParameterTypeEnum.ABV)) {
            this.abv = p;
        }

        if (p.getType().equals(ParameterTypeEnum.GRAVITY)) {
            if (this.og == null) {
                this.og = p;
            } else {
                this.fg = p;
            }
        }

        if (p.getType().equals(ParameterTypeEnum.EBC)) {
            this.ebc = p;
        }

        if (p.getType().equals(ParameterTypeEnum.IBU)) {
            this.ibu = p;
        }
    }
}
