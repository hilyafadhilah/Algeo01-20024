package app.utils;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Utilities for <code>Collection</code>.
 * 
 * @author Hilya Fadhilah Imania<hilyafadhilah@gmail.com>
 * @version 0.1.1
 * @see java.util.Collection
 * @since 2021-09-26
 */
public final class CollectionUtils {
  /**
   * Find using a lambda <code>filter</code> in <code>col</code>.
   * 
   * @param <T>    Type of collection elements
   * @param col    The collection
   * @param filter The lambda
   * @return The element which returns <code>true</code> when passed to
   *         <code>filter</code>, <code>null</code> if not found
   */
  public static <T> T find(Collection<T> col, Predicate<T> filter) {
    return col.stream().filter(filter).findFirst().orElse(null);
  }
}
