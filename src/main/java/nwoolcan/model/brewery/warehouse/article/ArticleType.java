package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.utils.StringUtils;

/**
 * The type of {@link Article} which can be stored in the warehouse.
 */
public enum ArticleType {

    /**
     * The warehouse can store ingredients, finished beers and miscellaneous articles.
     */
    INGREDIENT, FINISHED_BEER, MISC;

    @Override
    public String toString() {
        return StringUtils.underscoreSeparatedToHuman(super.toString());
    }

}
