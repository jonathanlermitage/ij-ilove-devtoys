package lermitage.intellij.ilovedevtoys.tools;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Version;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.UUID;

public class HashTools {
    private static SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());

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

    public static String generateBCrypt(String input, Version version) {
        try {
            if (input.isBlank()) {
                return "";
            }

            char[] bcryptChars = BCrypt.with(version).hashToChar(6, input.toCharArray());
            return new String(bcryptChars);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
