package lermitage.intellij.ilovedevtoys.tools.hash;

import java.util.Optional;

/**
 * Hash method to be implemented by each hash algorithm.
 */
public interface HashMethod {
    Optional<String> generateHash(String input);

    String name();

    default boolean match(String input, String hash) {
        return generateHash(input).map(hash::equals).orElse(false);
    }
}
