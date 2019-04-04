package nwoolcan.controller.viewModel.warehouse.stock;

/**
 * View-Model interface with methods callable to obtain information about the {@link nwoolcan.model.brewery.warehouse.stock.Stock}.
 */
public interface ViewStock {

    /**
     * Return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the name of the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    String getArticleName();
    /**
     * Return the string representation of the remaining {@link nwoolcan.model.utils.Quantity}.
     * @return the string representation of the remaining {@link nwoolcan.model.utils.Quantity}.
     */
    String getRemainingQuantity();
    /**
     * Return the string representation of the used {@link nwoolcan.model.utils.Quantity}.
     * @return the string representation of the used {@link nwoolcan.model.utils.Quantity}.
     */
    String getUsedQuantity();
    /**
     * Return the string representation of the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     * @return the string representation of the current {@link nwoolcan.model.brewery.warehouse.stock.StockState}.
     */
    String getState();
    /**
     * Return the string representation of the expiration {@link java.util.Date}.
     * @return the string representation of the expiration {@link java.util.Date}.
     */
    String getExpirationDate();
}
