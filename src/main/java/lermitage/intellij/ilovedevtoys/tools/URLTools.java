package lermitage.intellij.ilovedevtoys.tools;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLTools {

    public static String encodeURL(String decoded) {
        try {
            if (decoded.isBlank()) {
                return "";
            }
            return URLEncoder.encode(decoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String decodeURL(String encoded) {
        try {
            if (encoded.isBlank()) {
                return "";
            }
            return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
