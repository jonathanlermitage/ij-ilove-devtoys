package lermitage.intellij.ilovedevtoys.tools;

import java.util.UUID;

public class UUIDTools {

    public static String generateUUIDs(int howMany) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < howMany; i++) {
            sb.append(UUID.randomUUID()).append("\n");
        }
        return sb.toString().trim();
    }
}
