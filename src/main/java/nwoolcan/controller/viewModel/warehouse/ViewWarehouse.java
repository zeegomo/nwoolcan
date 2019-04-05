package nwoolcan.controller.viewModel.warehouse;

/**
 * View-Model representation of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
 */
public interface ViewWarehouse {

    /**
     * Return the number of {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     * @return the number of {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
     */
    int getNumberOfStocks();
    /**
     * Return the number of {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the number of {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    int getNumberOfArticles();

}
