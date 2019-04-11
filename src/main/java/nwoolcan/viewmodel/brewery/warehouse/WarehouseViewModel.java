package nwoolcan.viewmodel.brewery.warehouse;

import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStockBuilder;
import nwoolcan.model.brewery.warehouse.stock.StockState;
import nwoolcan.viewmodel.brewery.warehouse.article.AbstractArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.MiscArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.AbstractStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.BeerStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.StockViewModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ViewWarehouse: general info section.
 */
public class WarehouseViewModel {

    private static final QueryStock GENERAL_QUERY_STOCK = new QueryStockBuilder().build().getValue();
    private static final QueryArticle GENERAL_QUERY_ARTICLE = new QueryArticleBuilder().build();
    private static final QueryStock BEER_AVAILABLE_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.AVAILABLE)
                                                                                  .setArticleType(ArticleType.FINISHED_BEER)
                                                                                  .build().getValue();
    private static final QueryStock BEER_USED_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.USED_UP)
                                                                             .setArticleType(ArticleType.FINISHED_BEER)
                                                                             .build().getValue();
    private static final QueryStock BEER_EXPIRED_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.EXPIRED)
                                                                                .setArticleType(ArticleType.FINISHED_BEER)
                                                                                .build().getValue();
    private static final QueryStock INGREDIENT_AVAILABLE_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.AVAILABLE)
                                                                                       .setArticleType(ArticleType.INGREDIENT)
                                                                                       .build().getValue();
    private static final QueryStock INGREDIENT_USED_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.USED_UP)
                                                                                   .setArticleType(ArticleType.INGREDIENT)
                                                                                   .build().getValue();
    private static final QueryStock INGREDIENT_EXPIRED_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.EXPIRED)
                                                                                      .setArticleType(ArticleType.INGREDIENT)
                                                                                      .build().getValue();
    private static final QueryStock MISC_AVAILABLE_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.AVAILABLE)
                                                                                  .setArticleType(ArticleType.MISC)
                                                                                  .build().getValue();
    private static final QueryStock MISC_USED_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.USED_UP)
                                                                             .setArticleType(ArticleType.MISC)
                                                                             .build().getValue();
    private static final QueryStock MISC_EXPIRED_QUERY = new QueryStockBuilder().setIncludeOnlyStockState(StockState.EXPIRED)
                                                                                .setArticleType(ArticleType.MISC)
                                                                                .build().getValue();
    private final int nBeerAvailable;
    private final int nMiscAvailable;
    private final int nIngredientAvailable;
    private final int nBeerExpired;
    private final int nMiscExpired;
    private final int nIngredientExpired;
    private final int nBeerUsed;
    private final int nMiscUsed;
    private final int nIngredientUsed;
    private final List<AbstractStockViewModel> allStocks;
    private final List<AbstractArticleViewModel> allArticles;

    /**
     * Constructor of the view part of the {@link nwoolcan.model.brewery.warehouse.Warehouse} which specifies the general statistics.
     * @param warehouse to be converted in {@link WarehouseViewModel}.
     */
    public WarehouseViewModel(final Warehouse warehouse) {
        this.nBeerAvailable = warehouse.getStocks(BEER_AVAILABLE_QUERY).size();
        this.nMiscAvailable = warehouse.getStocks(MISC_AVAILABLE_QUERY).size();
        this.nIngredientAvailable = warehouse.getStocks(INGREDIENT_AVAILABLE_QUERY).size();
        this.nBeerExpired = warehouse.getStocks(BEER_EXPIRED_QUERY).size();
        this.nMiscExpired = warehouse.getStocks(MISC_EXPIRED_QUERY).size();
        this.nIngredientExpired = warehouse.getStocks(INGREDIENT_EXPIRED_QUERY).size();
        this.nBeerUsed = warehouse.getStocks(BEER_USED_QUERY).size();
        this.nMiscUsed = warehouse.getStocks(MISC_USED_QUERY).size();
        this.nIngredientUsed = warehouse.getStocks(INGREDIENT_USED_QUERY).size();
        this.allStocks = warehouse.getStocks(GENERAL_QUERY_STOCK)
                                  .stream()
                                  .map(stock -> {
                                      if (stock.getArticle().getArticleType() == ArticleType.FINISHED_BEER) {
                                          return new BeerStockViewModel((BeerStock) stock);
                                      }
                                      return new StockViewModel(stock);
                                  })
                                  .collect(Collectors.toList());
        this.allArticles = warehouse.getArticles(GENERAL_QUERY_ARTICLE)
                                    .stream()
                                    .map(article -> {
                                        switch (article.getArticleType()) {
                                            case FINISHED_BEER:
                                                return new BeerArticleViewModel(article.toBeerArticle().getValue());
                                            case INGREDIENT:
                                                return new IngredientArticleViewModel(article.toIngredientArticle().getValue());
                                            default:
                                            case MISC:
                                                return new MiscArticleViewModel(article);
                                        }
                                    })
                                    .collect(Collectors.toList());
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
    /**
     * Returns the list of all {@link AbstractStockViewModel}.
     * @return a list of {@link AbstractStockViewModel}.
     */
    public List<AbstractStockViewModel> getStocks() {
        return allStocks;
    }
    /**
     * Returns a {@link List} of {@link AbstractArticleViewModel}.
     * @return a {@link List} of {@link AbstractArticleViewModel}.
     */
    public List<AbstractArticleViewModel> getArticles() {
        return allArticles;
    }

}
