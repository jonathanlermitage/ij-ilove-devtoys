package lermitage.intellij.ilovedevtoys.tools;

import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class ASCIIHEXTools {
    private static final Pattern COMPILE = Pattern.compile("^[a-fA-F0-9 ]*$");

    public static String asciiToHex(String text, boolean withSpaces) {
        if (StandardCharsets.US_ASCII.newEncoder().canEncode(text)) {
            String hex = Hex.toHexString(text.getBytes(StandardCharsets.US_ASCII)).toUpperCase();
            if (withSpaces) {
                StringBuilder builder = new StringBuilder(hex.length() * 2);
                for (int i = 0; i < hex.length(); ) {
                    if (i != 0) {
                        builder.append(' ');
                    }
                    builder.append(hex.charAt(i++));
                    builder.append(hex.charAt(i++));
                }
                hex = builder.toString();
            }
            return hex;
        }
        return "Error: non ASCII characters";
    }

    public static String hexToAscii(String text) {
        if (COMPILE.matcher(text).matches()) {
            text = text.replace(" ", "");
            if (text.length() % 2 != 0) {
                text = text.substring(0, text.length() - 1);
            }
            return new String(Hex.decode(text), StandardCharsets.US_ASCII);
        }
        return "Error: non HEX characters";
    }
}
