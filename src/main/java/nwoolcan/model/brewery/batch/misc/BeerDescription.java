package nwoolcan.model.brewery.batch.misc;

import java.util.Optional;

/**
 * BeerDescription.
 */
public interface BeerDescription {
    /**
     * Return the name of the beer.
     * @return a {@link String}.
     */
    String getName();
    /**
     * Set the name of the beer.
     * @param name the name of the beer.
     */
    void setName(String name);
    /**
     * Return the style of the beer (es 'Doppelbock').
     * @return a {@link String}.
     */
    String getStyle();
    /**
     * Set the style of the beer (es 'Doppelbock').
     * @param style  the style of the beer.
     */
    void setStyle(String style);
    /**
     * Return the category of the style, if available (es 'Dark European Lager').
     * @return a {@link String}.
     */
    Optional<String> getStyleCategory();
    /**
     * Set the style category of the style, if available (es 'Dark European Lager').
     * @param styleCategory the style category of the beer.
     */
    void setStyleCategory(String styleCategory);
}
