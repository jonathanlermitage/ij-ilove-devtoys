package lermitage.intellij.ilovedevtoys.tools.hash;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Version;

import java.util.Optional;

public class BCryptHashMethod implements HashMethod {
    private final BCryptVersion version;

    public BCryptHashMethod(BCryptVersion version) {
        this.version = version;
    }

    @Override
    public Optional<String> generateHash(String input) {
        try {
            if (input.isBlank()) {
                return Optional.of("");
            }

            char[] bcryptChars = BCrypt.with(version.getVersion()).hashToChar(6, input.toCharArray());
            return Optional.of(new String(bcryptChars));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String name() {
        return version.name();
    }

    @Override
    public boolean match(String input, String hash) {
        return BCrypt.verifyer(version.getVersion()).verify(input.toCharArray(), hash).verified;
    }
}
