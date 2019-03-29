package nwoolcan.model.brewery.production.batch.misc;

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
     * Return the style of the beer (es 'Doppelbock').
     * @return a {@link String}.
     */
    String getStyle();
    /**
     * Return the category of the style, if available (es 'Dark European Lager').
     * @return a {@link String}.
     */
    Optional<String> getStyleCategory();
}
