package nwoolcan.controller.brewery.warehouse;

import nwoolcan.model.brewery.BreweryImpl;
import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;
import nwoolcan.viewmodel.brewery.warehouse.WarehouseViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.ArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.BeerArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.IngredientArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.article.MiscArticleViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.AbstractStockViewModel;
import nwoolcan.viewmodel.brewery.warehouse.stock.StockViewModel;

import java.util.Date;
import java.util.List;

public class WarehouseControllerImpl implements WarehouseController {

    private final Warehouse warehouse = BreweryImpl.getInstance().getWarehouse();

    @Override
    public List<ArticleViewModel> getArticles(QueryArticle queryArticle) {
        //return warehouse.getArticles(queryArticle).stream().map().collect(Collectors.toList());
        return null;
    }

    @Override
    public List<AbstractStockViewModel> getStocks(QueryStock queryStock) {
        return null;
    }

    @Override
    public WarehouseViewModel getWarhouseViewModel() {
        return null;
    }

    @Override
    public MiscArticleViewModel createMiscArticle(String name, UnitOfMeasure unitOfMeasure) {
        return null;
    }

    @Override
    public BeerArticleViewModel createBeerArticle(String name, UnitOfMeasure unitOfMeasure) {
        return null;
    }

    @Override
    public IngredientArticleViewModel createIngredientArticle(String name, UnitOfMeasure unitOfMeasure, IngredientType ingredientType) {
        return null;
    }

    @Override
    public Result<StockViewModel> createStock(int articleId, Date expirationDate) {
        return null;
    }

    @Override
    public Result<StockViewModel> createStock(int articleId) {
        return null;
    }

    @Override
    public Result<ArticleViewModel> setName(int articleId, String newName) {
        return null;
    }
}
