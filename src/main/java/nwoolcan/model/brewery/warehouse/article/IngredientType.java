package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.utils.StringUtils;

/**
 * The Type of {@link IngredientArticle} which can be stored in the warehouse.
 */
public enum IngredientType {

    /**
     * The warehouse can store these type of ingredients: Hops, Fermentables, Yeasts and others.
     */
    HOPS, FERMENTABLE, YEAST, OTHER;

    @Override
    public String toString() {
        return StringUtils.underscoreSeparatedToHuman(super.toString());
    }

}
