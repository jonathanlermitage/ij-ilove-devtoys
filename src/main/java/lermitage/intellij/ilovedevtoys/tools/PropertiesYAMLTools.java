package lermitage.intellij.ilovedevtoys.tools;

import io.codearte.props2yaml.Props2YAML;

public class PropertiesYAMLTools {

    private static final String SPRING_PROPERTIES_PROFILE_SEPARATOR = "#---";
    private static final String SPRING_YAML_PROFILE_SEPARATOR = "---";

    public enum PropertiesType {
        REGULAR,
        SPRING
    }

    public static String propertiesToYaml(String properties, PropertiesType propertiesType) {
        try {
            if (properties.isBlank()) {
                return "";
            }
            if (propertiesType == PropertiesType.SPRING) {
                String[] propsArray = properties.split(SPRING_PROPERTIES_PROFILE_SEPARATOR);
                StringBuilder sb = new StringBuilder();
                for (String props : propsArray) {
                    props = props.replace(SPRING_PROPERTIES_PROFILE_SEPARATOR, "").trim();
                    sb.append(Props2YAML.fromContent(props).convert()).append(SPRING_YAML_PROFILE_SEPARATOR).append("\n");
                }
                String result = sb.toString();
                if (result.endsWith(SPRING_YAML_PROFILE_SEPARATOR + "\n")) {
                    result = result.substring(0, result.length() - (SPRING_YAML_PROFILE_SEPARATOR + "\n").length());
                }
                return result.trim();
            }
            return Props2YAML.fromContent(properties).convert().trim();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
