package lermitage.intellij.ilovedevtoys.tools;

import org.apache.commons.codec.digest.DigestUtils;

public class HashTools {

    public static String generateMD5(String input) {
        try {
            if (input.isBlank()) {
                return "";
            }
            return DigestUtils.md5Hex(input);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String generateSHA1(String input) {
        try {
            if (input.isBlank()) {
                return "";
            }
            return DigestUtils.sha1Hex(input);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String generateSHA256(String input) {
        try {
            if (input.isBlank()) {
                return "";
            }
            return DigestUtils.sha256Hex(input);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String generateSHA384(String input) {
        try {
            if (input.isBlank()) {
                return "";
            }
            return DigestUtils.sha384Hex(input);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String generateSHA512(String input) {
        try {
            if (input.isBlank()) {
                return "";
            }
            return DigestUtils.sha512Hex(input);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
