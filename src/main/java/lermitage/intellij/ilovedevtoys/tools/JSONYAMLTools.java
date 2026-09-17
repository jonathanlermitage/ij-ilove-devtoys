package lermitage.intellij.ilovedevtoys.tools;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.yaml.YAMLFactory;
import tools.jackson.dataformat.yaml.YAMLMapper;

public class JSONYAMLTools {

    public static String yamlToJson(String yaml) {
        try {
            if (yaml.isBlank()) {
                return "";
            }
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            Object obj = yamlReader.readValue(yaml, Object.class);
            ObjectMapper jsonWriter = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
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
