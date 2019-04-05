package nwoolcan.controller.warehouse;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
 */
public final class ViewWarehouse {

    private final int numberOfStocks;
    private final int numberOfArticles;

    /**
     * Constructor of the view-warehouse.
     * @param numberOfStocks the total number of registered {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @param numberOfArticles the total number of registered {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    // Package-private
    ViewWarehouse(final int numberOfStocks, final int numberOfArticles) {
        this.numberOfStocks = numberOfStocks;
        this.numberOfArticles = numberOfArticles;
    }
    /**
     * Return the number of {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @return the number of {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    public int getNumberOfStocks() {
        return numberOfStocks;
    }
    /**
     * Return the number of {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the number of {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public int getNumberOfArticles() {
        return numberOfArticles;
    }

}
