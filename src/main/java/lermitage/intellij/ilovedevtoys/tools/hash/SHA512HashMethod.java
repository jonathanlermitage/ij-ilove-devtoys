package lermitage.intellij.ilovedevtoys.tools.hash;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class SHA512HashMethod implements HashMethod {
    public static final String NAME = "SHA512";

    @Override
    public Optional<String> generateHash(String input) {
        try {
            if (input.isBlank()) {
                return Optional.of("");
            }
            return Optional.of(DigestUtils.sha512Hex(input));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String name() {
        return NAME;
    }
}
