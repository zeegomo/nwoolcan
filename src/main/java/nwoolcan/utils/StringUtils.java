package nwoolcan.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Some utilities about strings.
 */
public final class StringUtils {

    private StringUtils() { }

    /**
     * Converts a string to all lowercase with first letter upper case.
     * @param toConvert string to be capitalized.
     * @return the capitalized string.
     */
    public static String capitalize(final String toConvert) {
        return toConvert.substring(0, 1).toUpperCase() + toConvert.substring(1).toLowerCase();
    }

    /**
     * Converts a string of words separated by underscore "UPPER_UNDERSCORE" to "Human Readable".
     * For example from "MAGO_iulius" to "Mago Iulius".
     * @param toConvert the underscore separated string to convert
     * @return the human readable string
     */
    public static String underscoreSeparatedToHuman(final String toConvert) {
        return Arrays.stream(toConvert.split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }

    public static void split() {
        "".split(":");
    }
}
