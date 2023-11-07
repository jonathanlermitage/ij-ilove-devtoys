package lermitage.intellij.ilovedevtoys.tools;

import org.apache.commons.text.StringEscapeUtils;

public class EscapeTools {

    public enum EscapeType {
        CSV,
        HTML,
        JAVA,
        JAVASCRIPT,
        JSON,
        XML
    }

    public static String escape(String unescaped, EscapeType escapeType) {
        try {
            if (unescaped.isEmpty()) {
                return "";
            }
            return switch (escapeType) {
                case CSV -> StringEscapeUtils.escapeCsv(unescaped);
                case HTML -> StringEscapeUtils.escapeHtml4(unescaped);
                case JAVA -> StringEscapeUtils.escapeJava(unescaped);
                case JAVASCRIPT -> StringEscapeUtils.escapeEcmaScript(unescaped);
                case JSON -> org.apache.commons.text.StringEscapeUtils.escapeJson(unescaped);
                case XML -> StringEscapeUtils.escapeXml11(unescaped);
            };
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String unescape(String escaped, EscapeType escapeType) {
        try {
            if (escaped.isEmpty()) {
                return "";
            }
            return switch (escapeType) {
                case CSV -> StringEscapeUtils.unescapeCsv(escaped);
                case HTML -> StringEscapeUtils.unescapeHtml4(escaped);
                case JAVA -> StringEscapeUtils.unescapeJava(escaped);
                case JAVASCRIPT -> StringEscapeUtils.unescapeEcmaScript(escaped);
                case JSON -> org.apache.commons.text.StringEscapeUtils.unescapeJson(escaped);
                case XML -> StringEscapeUtils.unescapeXml(escaped);
            };
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
