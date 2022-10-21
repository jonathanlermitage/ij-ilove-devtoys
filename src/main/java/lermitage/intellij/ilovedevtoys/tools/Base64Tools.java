package lermitage.intellij.ilovedevtoys.tools;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Tools {

    private static final Base64.Decoder B64_DECODER = Base64.getDecoder();
    private static final Base64.Encoder B64_ENCODER = Base64.getEncoder();

    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final Charset US_ASCII = StandardCharsets.US_ASCII;

    public static String toBase64(String text, Charset charset) {
        if (text.isBlank()) {
            return "";
        }
        try {
            return B64_ENCODER.encodeToString(text.getBytes(charset));
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String toText(String base64, Charset charset) {
        try {
            if (base64.isBlank()) {
                return "";
            }
            return new String(B64_DECODER.decode(base64), charset);

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
