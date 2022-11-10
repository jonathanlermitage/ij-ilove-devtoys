package lermitage.intellij.ilovedevtoys.tools;

import org.apache.commons.lang.StringEscapeUtils;

public class EscapeTools {

    public enum EscapeType {
        CSV,
        HTML,
        JAVA,
        JAVASCRIPT,
        XML
    }

    public static String escape(String unescaped, EscapeType escapeType) {
        try {
            if (unescaped.isEmpty()) {
                return "";
            }
            return switch (escapeType) {
                case CSV -> StringEscapeUtils.escapeCsv(unescaped);
                case HTML -> StringEscapeUtils.escapeHtml(unescaped);
                case JAVA -> StringEscapeUtils.escapeJava(unescaped);
                case JAVASCRIPT -> StringEscapeUtils.escapeJavaScript(unescaped);
                case XML -> StringEscapeUtils.escapeXml(unescaped);
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
                case HTML -> StringEscapeUtils.unescapeHtml(escaped);
                case JAVA -> StringEscapeUtils.unescapeJava(escaped);
                case JAVASCRIPT -> StringEscapeUtils.unescapeJavaScript(escaped);
                case XML -> StringEscapeUtils.unescapeXml(escaped);
            };
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
