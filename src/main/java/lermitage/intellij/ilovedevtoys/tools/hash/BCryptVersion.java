package lermitage.intellij.ilovedevtoys.tools.hash;

import at.favre.lib.crypto.bcrypt.BCrypt.Version;

public enum BCryptVersion {
    BCryptVersion2a(Version.VERSION_2A),
    BCryptVersion2b(Version.VERSION_2B),
    BCryptVersion2y(Version.VERSION_2Y);

    private final Version version;

    BCryptVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }
}
