package io.codearte.props2yaml;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class Props2YAML {

    private final Properties properties = new Properties();

    Props2YAML(String source) throws IOException {
        properties.load(new StringReader(source));
    }

    public static Props2YAML fromContent(String content) throws IOException {
        return new Props2YAML(content);
    }

    public String convert(boolean useNumericKeysAsArrayIndexes) {
        PropertyTree tree = new TreeBuilder(properties, useNumericKeysAsArrayIndexes).build();
        tree = new ArrayProcessor(tree).apply();
        return tree.toYAML();
    }

    public String convert() {
        return convert(true);
    }
}
