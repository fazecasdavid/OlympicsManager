package ro.ubb.olympics.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Utility class representing a pair of two objects.
 *
 * @param <T1> the type of the first object
 * @param <T2> the type of the second object
 */
@Getter
@AllArgsConstructor
public class Pair<T1, T2> {

    private final T1 first;
    private final T2 second;

}