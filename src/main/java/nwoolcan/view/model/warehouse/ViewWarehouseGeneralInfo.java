package nwoolcan.view.model.warehouse;

public class ViewWarehouseGeneralInfo {

    private final int nBeerAvailable;
    private final int nMiscAvailable;
    private final int nIngredientAvailable;
    private final int nBeerExpired;
    private final int nMiscExpired;
    private final int nIngredientExpired;
    private final int nBeerUsed;
    private final int nMiscUsed;
    private final int nIngredientUsed;

    /**
     * Constructor of the view part of the {@link nwoolcan.model.brewery.warehouse.Warehouse} which specifies the general statistics.
     * @param nBeerAvailable of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nMiscAvailable of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nIngredientAvailable of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nBeerExpired of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nMiscExpired of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nIngredientExpired of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nBeerUsed of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nMiscUsed of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     * @param nIngredientUsed of the {@link nwoolcan.model.brewery.warehouse.Warehouse}.
     */
    public ViewWarehouseGeneralInfo(final int nBeerAvailable,
                                    final int nMiscAvailable,
                                    final int nIngredientAvailable,
                                    final int nBeerExpired,
                                    final int nMiscExpired,
                                    final int nIngredientExpired,
                                    final int nBeerUsed,
                                    final int nMiscUsed,
                                    final int nIngredientUsed) {
        this.nBeerAvailable = nBeerAvailable;
        this.nMiscAvailable = nMiscAvailable;
        this.nIngredientAvailable = nIngredientAvailable;
        this.nBeerExpired = nBeerExpired;
        this.nMiscExpired = nMiscExpired;
        this.nIngredientExpired = nIngredientExpired;
        this.nBeerUsed = nBeerUsed;
        this.nMiscUsed = nMiscUsed;
        this.nIngredientUsed = nIngredientUsed;
    }
    /**
     * Return the number of available {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @return the number of available {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     */
    public int getnBeerAvailable() {
        return nBeerAvailable;
    }
    /**
     * Return the number of available {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the number of available {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public int getnMiscAvailable() {
        return nMiscAvailable;
    }
    /**
     * Return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     */
    public int getnIngredientAvailable() {
        return nIngredientAvailable;
    }
    /**
     * Return the number of expired {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @return the number of expired {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     */
    public int getnBeerExpired() {
        return nBeerExpired;
    }
    /**
     * Return the number of expired {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the number of expired {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public int getnMiscExpired() {
        return nMiscExpired;
    }
    /**
     * Return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     */
    public int getnIngredientExpired() {
        return nIngredientExpired;
    }
    /**
     * Return the number of used {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     * @return the number of used {@link nwoolcan.model.brewery.warehouse.stock.BeerStock}.
     */
    public int getnBeerUsed() {
        return nBeerUsed;
    }
    /**
     * Return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     * @return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with a misc {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public int getnMiscUsed() {
        return nMiscUsed;
    }
    /**
     * Return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     * @return the number of used {@link nwoolcan.model.brewery.warehouse.stock.Stock} with an {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle}.
     */
    public int getnIngredientUsed() {
        return nIngredientUsed;
    }


}
