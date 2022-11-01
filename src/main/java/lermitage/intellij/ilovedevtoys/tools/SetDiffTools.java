package lermitage.intellij.ilovedevtoys.tools;

import java.util.Arrays;
import java.util.List;

public class SetDiffTools {

    public static String compareSets(String set1, String set2, boolean caseSensitive, boolean ignoreEmptyLines) {
        try {
            List<String> set1OrderedLines = Arrays.stream(set1.split("\n")).toList();
            List<String> set2OrderedLines = Arrays.stream(set2.split("\n")).toList();
            List<String> set1OrderedLinesUpperCase = Arrays.stream(set1.toUpperCase().split("\n")).toList();
            List<String> set2OrderedLinesUpperCase = Arrays.stream(set2.toUpperCase().split("\n")).toList();
            List<String> set1ToCompareWith = caseSensitive ? set1OrderedLines : set1OrderedLinesUpperCase;
            List<String> set2ToCompareWith = caseSensitive ? set2OrderedLines : set2OrderedLinesUpperCase;

            StringBuilder sb = new StringBuilder();
            for (String line : set1OrderedLines) {
                if (!line.isEmpty() || !ignoreEmptyLines) {
                    if (!set2ToCompareWith.contains(caseSensitive ? line : line.toUpperCase())) {
                        sb.append(">>> line '").append(line).append("' from Set 1 is missing in Set 2\n");
                    }
                }
            }
            for (String line : set2OrderedLines) {
                if (!line.isEmpty() || !ignoreEmptyLines) {
                    if (!set1ToCompareWith.contains(caseSensitive ? line : line.toUpperCase())) {
                        sb.append("<<< line '").append(line).append("' from Set 2 is missing in Set 1\n");
                    }
                }
            }
            if (sb.isEmpty()) {
                return "OK, both sets have same content :-)";
            }
            return "Found some differences!\n" + sb.toString().trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
