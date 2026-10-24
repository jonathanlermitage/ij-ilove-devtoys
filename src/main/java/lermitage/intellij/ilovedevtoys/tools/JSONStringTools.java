package lermitage.intellij.ilovedevtoys.tools;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class JSONStringTools {

    public static String jsonToString(String json) {
        try {
            if (json.isBlank()) {
                return "";
            }
            JsonNode jsonNodeTree = new ObjectMapper().readTree(json);
            String escapedJson = jsonNodeTree.toString()
                .replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\"", "\\\\\"");
            return "\"" + escapedJson + "\"";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
