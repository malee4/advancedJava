package Final;

import java.util.function.Function;

/**
 * Functional interface to allow for Lambda functions that take in three arguments and return one value
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    /**
     * Executes the function for the given arguments
     * 
     * @param t Argument 1
     * @param u Argument 2
     * @param v Argument 3
     * @return Return value
     */
    R apply(T t, U u, V v);

    default <K> TriFunction<T, U, V, K> andThen(Function<? super R, ? extends K> after) {
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}
