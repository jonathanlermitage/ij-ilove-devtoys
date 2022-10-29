package lermitage.intellij.ilovedevtoys.tools;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.BencodeInputStream;
import com.dampcake.bencode.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public class BENCODEJSONTools {

    public static String bencodeToJson(String bencodeString) {
        try {
            if (bencodeString.isBlank()) {
                return "";
            }

            ByteArrayInputStream in = new ByteArrayInputStream(bencodeString.getBytes());
            BencodeInputStream bencode = new BencodeInputStream(in);

            Type type = bencode.nextType();
            if (type == Type.STRING) {
                return bencode.readString();
            }

            if (type == Type.NUMBER) {
                return Long.toString(bencode.readNumber());
            }

            if (type == Type.LIST) {
                List<Object> list = bencode.readList();
                return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(list);
            }

            if (type == Type.DICTIONARY) {
                Map<String, Object> map = bencode.readDictionary();
                return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(map);
            }

            return "Type is UNKNOWN";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String jsonToBencode(String json) {
        try {
            if (json.isBlank()) {
                return "";
            }

            json = json.trim();
            if (json.charAt(0) == '{') {
                try {
                    return new String(new Bencode().encode(new ObjectMapper().readValue(json, Map.class)));
                } catch (JsonProcessingException e) {
                    // it failed, then encode it as a String
                }
            } else if (json.charAt(0) == '[') {
                try {
                    return new String(new Bencode().encode(new ObjectMapper().readValue(json, List.class)));
                } catch (JsonProcessingException e) {
                    // it failed, then encode it as a String
                }
            }
            try {
                return new String(new Bencode().encode(Integer.parseInt(json)));
            } catch (NumberFormatException e) {
                // it failed, then encode it as a String
            }
            return new String(new Bencode().encode(json));
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
