package nwoolcan.viewmodel.brewery.warehouse.article;

import nwoolcan.model.brewery.warehouse.Warehouse;
import nwoolcan.model.brewery.warehouse.article.ArticleType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.article.QueryArticleBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ArticlesViewModel: general info section.
 */
public final class ArticlesViewModel {

    private static final QueryArticle GENERAL_QUERY_ARTICLE = new QueryArticleBuilder().build();
    private static final QueryArticle QUERY_BEER_ARTICLE = new QueryArticleBuilder().setIncludeArticleType(ArticleType.FINISHED_BEER)
                                                                                    .build();
    private static final QueryArticle QUERY_MISC_ARTICLE = new QueryArticleBuilder().setIncludeArticleType(ArticleType.MISC)
                                                                                    .build();
    private static final QueryArticle QUERY_INGREDIENT_ARTICLE = new QueryArticleBuilder().setIncludeArticleType(ArticleType.INGREDIENT)
                                                                                          .build();
    private final int nBeerArticles;
    private final int nMiscArticles;
    private final int nIngredientArticles;
    private final List<AbstractArticleViewModel> allArticles;

    /**
     * Constructor of the {@link ArticlesViewModel}.
     * @param warehouse needed to collect information about the {@link nwoolcan.model.brewery.warehouse.article.Article}.
     */
    public ArticlesViewModel(final Warehouse warehouse) {
        this.nBeerArticles = warehouse.getArticles(QUERY_BEER_ARTICLE).size();
        this.nMiscArticles = warehouse.getArticles(QUERY_MISC_ARTICLE).size();
        this.nIngredientArticles = warehouse.getArticles(QUERY_INGREDIENT_ARTICLE).size();
        this.allArticles = warehouse.getArticles(GENERAL_QUERY_ARTICLE)
                                    .stream()
                                    .map(AbstractArticleViewModel::getViewArticle)
                                    .collect(Collectors.toList());
    }

    /**
     * Returns a {@link List} of {@link AbstractArticleViewModel}.
     * @return a {@link List} of {@link AbstractArticleViewModel}.
     */
    public List<AbstractArticleViewModel> getArticles() {
        return allArticles;
    }
    /**
     * Return the number of {@link nwoolcan.model.brewery.warehouse.article.BeerArticle} in the model.
     * @return the number of {@link nwoolcan.model.brewery.warehouse.article.BeerArticle} in the model.
     */
    public int getnBeerArticles() {
        return nBeerArticles;
    }
    /**
     * Return the number of {@link nwoolcan.model.brewery.warehouse.article.Article} in the model.
     * @return the number of {@link nwoolcan.model.brewery.warehouse.article.Article} in the model.
     */
    public int getnMiscArticles() {
        return nMiscArticles;
    }
    /**
     * Return the number of {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle} in the model.
     * @return the number of {@link nwoolcan.model.brewery.warehouse.article.IngredientArticle} in the model.
     */
    public int getnIngredientArticles() {
        return nIngredientArticles;
    }
}
