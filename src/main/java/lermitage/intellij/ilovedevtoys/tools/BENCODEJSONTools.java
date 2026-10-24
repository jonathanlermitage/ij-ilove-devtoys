package lermitage.intellij.ilovedevtoys.tools;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.BencodeInputStream;
import com.dampcake.bencode.Type;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class BENCODEJSONTools {

    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();

    public static String bencodeToJson(String bencodeString) {
        try {
            if (bencodeString.isBlank()) {
                return "";
            }

            ByteArrayInputStream in = new ByteArrayInputStream(bencodeString.getBytes(StandardCharsets.UTF_8));
            BencodeInputStream bencode = new BencodeInputStream(in, StandardCharsets.UTF_8, true);

            Type type = bencode.nextType();
            if (type == Type.STRING) {
                byte[] bytes = bencode.readStringBytes().array();
                return new String(bytes, StandardCharsets.UTF_8);
            }

            if (type == Type.NUMBER) {
                return Long.toString(bencode.readNumber());
            }

            if (type == Type.LIST) {
                List<Object> list = bencode.readList();
                return convertListToJson(list);
            }

            if (type == Type.DICTIONARY) {
                Map<String, Object> map = bencode.readDictionary();
                return convertMapToJson(map);
            }

            return "Type is UNKNOWN";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String convertListToJson(List<Object> list) throws Exception {
        List<Object> converted = new java.util.ArrayList<>();
        for (Object item : list) {
            converted.add(convertBencodeValue(item));
        }
        return OBJECT_MAPPER.writeValueAsString(converted);
    }

    private static String convertMapToJson(Map<String, Object> map) throws Exception {
        Map<String, Object> converted = new java.util.LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            converted.put(entry.getKey(), convertBencodeValue(entry.getValue()));
        }
        return OBJECT_MAPPER.writeValueAsString(converted);
    }

    private static Object convertBencodeValue(Object value) {
        if (value instanceof ByteBuffer) {
            byte[] bytes = ((ByteBuffer) value).array();
            return new String(bytes, StandardCharsets.UTF_8);
        }
        if (value instanceof List) {
            List<Object> list = (List<Object>) value;
            List<Object> converted = new java.util.ArrayList<>();
            for (Object item : list) {
                converted.add(convertBencodeValue(item));
            }
            return converted;
        }
        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            Map<String, Object> converted = new java.util.LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                converted.put(entry.getKey(), convertBencodeValue(entry.getValue()));
            }
            return converted;
        }
        return value;
    }

    public static String jsonToBencode(String json) {
        try {
            if (json.isBlank()) {
                return "";
            }

            json = json.trim();
            if (json.charAt(0) == '{') {
                try {
                    return new String(new Bencode().encode(new ObjectMapper().readValue(json, Map.class)), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    // it failed, then encode it as a String
                }
            } else if (json.charAt(0) == '[') {
                try {
                    return new String(new Bencode().encode(new ObjectMapper().readValue(json, List.class)), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    // it failed, then encode it as a String
                }
            } else if (json.charAt(0) == '"') {
                try {
                    String value = new ObjectMapper().readValue(json, String.class);
                    return new String(new Bencode().encode(value), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    // it failed, then encode it as a raw String
                }
            }
            try {
                return new String(new Bencode().encode(Integer.parseInt(json)), StandardCharsets.UTF_8);
            } catch (NumberFormatException e) {
                // it failed, then encode it as a String
            }
            return new String(new Bencode().encode(json), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
