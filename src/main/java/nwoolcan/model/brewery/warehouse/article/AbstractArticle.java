package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;

// Package-Private
abstract class AbstractArticle implements Article {

    private String name;
    private final Integer id;
    private final UnitOfMeasure unitOfMeasure;

    AbstractArticle(final Integer id, final String name, final UnitOfMeasure unitOfMeasure) {
        this.id = id;
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public final int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public final UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }
    /**
     * Package private setter for the name of the {@link Article}.
     * @param newName the new name of the {@link Article}.
     * @return this for fluency.
     */
    // Package-Private
    protected AbstractArticle setName(final String newName) {
        this.name = newName;
        return this;
    }
}
