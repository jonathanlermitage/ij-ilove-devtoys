package lermitage.intellij.ilovedevtoys.tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("DuplicatedCode")
public class LinesUtilsTools {

    public static String compareSets(String set1, String set2, boolean caseSensitive, boolean ignoreEmptyLines) {
        try {
            if (set1.isEmpty() || set2.isEmpty()) {
                return "";
            }
            List<String> set1OrderedLines = Arrays.stream(set1.split("\n")).toList();
            List<String> set2OrderedLines = Arrays.stream(set2.split("\n")).toList();
            List<String> set1OrderedLinesUpperCase = Arrays.stream(set1.toUpperCase().split("\n")).toList();
            List<String> set2OrderedLinesUpperCase = Arrays.stream(set2.toUpperCase().split("\n")).toList();
            List<String> set1ToCompareWith = caseSensitive ? set1OrderedLines : set1OrderedLinesUpperCase;
            List<String> set2ToCompareWith = caseSensitive ? set2OrderedLines : set2OrderedLinesUpperCase;

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < set1OrderedLines.size(); i++) {
                String line = set1OrderedLines.get(i);
                if (!line.isEmpty() || !ignoreEmptyLines) {
                    if (!set2ToCompareWith.contains(caseSensitive ? line : line.toUpperCase())) {
                        sb.append(">>> line ").append(i + 1).append(" '").append(line).append("' from Set 1 is missing in Set 2\n");
                    }
                }
            }
            for (int i = 0; i < set2OrderedLines.size(); i++) {
                String line = set2OrderedLines.get(i);
                if (!line.isEmpty() || !ignoreEmptyLines) {
                    if (!set1ToCompareWith.contains(caseSensitive ? line : line.toUpperCase())) {
                        sb.append("<<< line ").append(i + 1).append(" '").append(line).append("' from Set 2 is missing in Set 1\n");
                    }
                }
            }
            if (sb.isEmpty()) {
                return "=== OK, both sets have same content ===";
            }
            return "=== Found some differences! ===\n\n" + sb.toString().trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String mergeSets(String set1, String set2, boolean caseSensitive) {
        try {
            if (set1.isEmpty() || set2.isEmpty()) {
                return "";
            }
            List<String> set1OrderedLines = Arrays.stream(set1.split("\n")).toList();
            List<String> set2OrderedLines = Arrays.stream(set2.split("\n")).toList();
            List<String> set1OrderedLinesUpperCase = Arrays.stream(set1.toUpperCase().split("\n")).toList();
            List<String> set2OrderedLinesUpperCase = Arrays.stream(set2.toUpperCase().split("\n")).toList();
            List<String> set1ToCompareWith = caseSensitive ? set1OrderedLines : set1OrderedLinesUpperCase;
            List<String> set2ToCompareWith = caseSensitive ? set2OrderedLines : set2OrderedLinesUpperCase;

            int linesAdded = 0;
            StringBuilder sb = new StringBuilder();
            for (String set2ToCompareWithLine : set2ToCompareWith) {
                if (!set1ToCompareWith.contains(set2ToCompareWithLine)) {
                    sb.append(set2ToCompareWithLine).append("\n");
                    linesAdded++;
                }
            }

            String resultTitle;
            if (linesAdded > 0) {
                resultTitle = "=== Added " + linesAdded + " line(s). Merged result: ===\n\n";
            } else {
                resultTitle = "=== Found no lines to add! ===\n\n";
            }
            return resultTitle + set1 + "\n" + sb.toString().trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String subtractSets(String set1, String set2, boolean caseSensitive) {
        try {
            if (set1.isEmpty() || set2.isEmpty()) {
                return "";
            }
            List<String> set1OrderedLines = Arrays.stream(set1.split("\n")).toList();
            List<String> set2OrderedLines = Arrays.stream(set2.split("\n")).toList();
            List<String> set1OrderedLinesUpperCase = Arrays.stream(set1.toUpperCase().split("\n")).toList();
            List<String> set2OrderedLinesUpperCase = Arrays.stream(set2.toUpperCase().split("\n")).toList();
            List<String> set1ToCompareWith = caseSensitive ? set1OrderedLines : set1OrderedLinesUpperCase;
            List<String> set2ToCompareWith = caseSensitive ? set2OrderedLines : set2OrderedLinesUpperCase;

            int linesRemoved = 0;
            Set<String> linesToRemove = new HashSet<>();
            for (String set1ToCompareWithLine : set1ToCompareWith) {
                if (set2ToCompareWith.contains(set1ToCompareWithLine)) {
                    linesToRemove.add(set1ToCompareWithLine);
                    linesRemoved++;
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < set1ToCompareWith.size(); i++) {
                String set1Line = set1ToCompareWith.get(i);
                if (!linesToRemove.contains(set1Line)) {
                    sb.append(set1OrderedLines.get(i)).append("\n");
                }
            }

            String resultTitle;
            if (linesRemoved > 0) {
                resultTitle = "=== Removed " + linesRemoved + " line(s). Subtracted result: ===\n\n";
            } else {
                resultTitle = "=== Found no lines to remove! ===\n\n";
            }
            return resultTitle + sb.toString().trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
