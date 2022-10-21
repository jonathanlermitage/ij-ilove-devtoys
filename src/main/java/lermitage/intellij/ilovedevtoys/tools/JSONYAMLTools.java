package lermitage.intellij.ilovedevtoys.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class JSONYAMLTools {

    public static String yamlToJson(String yaml) {
        try {
            if (yaml.isBlank()) {
                return "";
            }
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            Object obj = yamlReader.readValue(yaml, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return jsonWriter.writeValueAsString(obj);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String jsonToYaml(String json) {
        try {
            if (json.isBlank()) {
                return "";
            }
            JsonNode jsonNodeTree = new ObjectMapper().readTree(json);
            return new YAMLMapper().writeValueAsString(jsonNodeTree);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
