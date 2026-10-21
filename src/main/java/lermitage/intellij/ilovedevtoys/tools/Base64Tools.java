package lermitage.intellij.ilovedevtoys.tools;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public static String toBase64(String text, Charset charset, String separator) {
        if (separator == null || separator.isEmpty()) {
            return toBase64(text, charset);
        }
        return transformEach(text, separator, value -> toBase64(value, charset));
    }

    public static String toText(String base64, Charset charset, String separator) {
        if (separator == null || separator.isEmpty()) {
            return toText(base64, charset);
        }
        return transformEach(base64, separator, value -> toText(value, charset));
    }

    private static String transformEach(String input, String separator, UnaryOperator<String> transformer) {
        return Arrays.stream(input.split(Pattern.quote(separator), -1))
            .map(transformer)
            .collect(Collectors.joining(separator));
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
