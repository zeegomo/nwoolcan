package nwoolcan.view.filters;

import java.util.Optional;

/**
 * A filter to be used in the GUI.
 * @param <T> the type of filter value.
 */
public interface GUIFilter<T> {
    /**
     * Returns the filter selected value. Empty optional if no value selected.
     * @return the filter selected value. Empty optional if no value selected.
     */
    Optional<T> getValue();
}
