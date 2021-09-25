package lib.utils;

import java.util.Collection;
import java.util.function.Predicate;

public final class CollectionUtils {
  public static <T> T find(Collection<T> col, Predicate<T> filter) {
    return col.stream().filter(filter).findFirst().orElse(null);
  }
}
