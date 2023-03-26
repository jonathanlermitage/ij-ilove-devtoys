package lermitage.intellij.ilovedevtoys.tools;

import lermitage.intellij.ilovedevtoys.tools.hash.*;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashTools {
    private static SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());
    private static final Map<String, HashMethod> hashMethods = new HashMap<>();

    static {
        registerHashMethod(new MD5HashMethod());
        registerHashMethod(new SHA1HashMethod());
        registerHashMethod(new SHA256HashMethod());
        registerHashMethod(new SHA384HashMethod());
        registerHashMethod(new SHA512HashMethod());
        for (BCryptVersion version : BCryptVersion.values()) {
            registerHashMethod(new BCryptHashMethod(version));
        }
    }

    public static String generateMD5(String input) {
        return generateHash(MD5HashMethod.NAME, input);
    }

    public static String generateSHA1(String input) {
        return generateHash(SHA1HashMethod.NAME, input);
    }

    public static String generateSHA256(String input) {
        return generateHash(SHA256HashMethod.NAME, input);
    }

    public static String generateSHA384(String input) {
        return generateHash(SHA384HashMethod.NAME, input);
    }

    public static String generateSHA512(String input) {
        return generateHash(SHA512HashMethod.NAME, input);
    }

    public static String generateBCrypt(String input, BCryptVersion version) {
        return generateHash(version.name(), input);
    }

    private static void registerHashMethod(HashMethod method) {
        hashMethods.put(method.name(), method);
    }

    @NotNull
    private static String generateHash(String method, String input) {
        return hashMethods
            .get(method)
            .generateHash(input)
            .orElse("Error: " + input);
    }

    public static String verifyPassword(String input, String hash) {
        if (input.isBlank()) {
            return "Password is empty";
        }
        if (hash.isBlank()) {
            return "Hash is empty";
        }
        // has to do that as BCrypt 2Y accepts all
        if (hash.startsWith("$2a$") && hashMethods.get(BCryptVersion.BCryptVersion2a.name()).match(input, hash)) {
            return "Password is valid for " + BCryptVersion.BCryptVersion2a.name();
        }
        if (hash.startsWith("$2b$") && hashMethods.get(BCryptVersion.BCryptVersion2b.name()).match(input, hash)) {
            return "Password is valid for " + BCryptVersion.BCryptVersion2b.name();
        }
        for (HashMethod method : hashMethods.values()) {
            if (method.match(input, hash)) {
                return "Password is valid for " + method.name();
            }
        }
        return "Unable to verify password";
    }
}
