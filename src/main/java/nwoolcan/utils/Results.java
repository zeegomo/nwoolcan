package nwoolcan.utils;

/**
 * Utilites for Result.
 */
public final class Results {

    private Results() { }

    /**
     * Returns a {@link Result} holding elem if elem is not null, otherwise holds a NullPointerException.
     * @param elem the elem to be tested
     * @param <T> elem type
     * @return a {@link Result} holding elem if elem is not null, otherwise holds a NullPointerException
     */
    public static <T> Result<T> requireNonNull(final T elem) {
        if (elem == null) {
            return Result.error(new NullPointerException());
        } else {
            return Result.ofNullable(elem);
        }
    }

}
