package lermitage.intellij.ilovedevtoys.tools;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class HMACTools {

    public static String generateHMAC(HmacAlgorithms algorithm, String input, String key) {
        try {
            if (input.isBlank()) {
                return "";
            }
            return new HmacUtils(algorithm.getName(), key).hmacHex(input);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
