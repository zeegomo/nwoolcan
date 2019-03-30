package nwoolcan.model.brewery.production.batch.misc;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * BeerDescription impl.
 */
public final class BeerDescriptionImpl implements BeerDescription {
    
    private String name;
    private String style;
    @Nullable
    private final String styleCategory;

    /**
     * New {@link BeerDescription}.
     * @param name the name of the beer.
     * @param style the style of the beer.
     * @param styleCategory the style category.
     */
    public BeerDescriptionImpl(final String name, final String style, final String styleCategory) {
        this.name = name;
        this.style = style;
        this.styleCategory = styleCategory;
    }
    /**
     * New {@link BeerDescription}.
     * @param name the name of the beer.
     * @param style the style of the beer.
     */
    public BeerDescriptionImpl(final String name, final String style) {
        this.name = name;
        this.style = style;
        this.styleCategory = null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getStyle() {
        return this.name;
    }

    @Override
    public Optional<String> getStyleCategory() {
        return Optional.ofNullable(styleCategory);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeerDescriptionImpl that = (BeerDescriptionImpl) o;
        return Objects.equals(name, that.name)
            && Objects.equals(style, that.style)
            && Objects.equals(styleCategory, that.styleCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, style, styleCategory);
    }
}
