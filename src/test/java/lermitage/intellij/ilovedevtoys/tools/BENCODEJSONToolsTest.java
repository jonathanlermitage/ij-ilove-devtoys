package lermitage.intellij.ilovedevtoys.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BENCODEJSONToolsTest {
    @Test
    void testBencodeNumber() {
        assertEquals("56", BENCODEJSONTools.bencodeToJson("i56e"));
    }

    @Test
    void testBencodeString() {
        assertEquals("hello world", BENCODEJSONTools.bencodeToJson("11:hello world"));

    }

    @Test
    void testBencodeToJsonList() {
        assertEquals("[ \"string\", 22, 3 ]", BENCODEJSONTools.bencodeToJson("l6:stringi22ei3ee"));

    }

    @Test
    void testBencodeToJsonObject() {
        assertEquals('{' + System.lineSeparator() +
                         "  \"dict\" : {" +System.lineSeparator() +
                         "    \"dict-item-1\" : \"test\"," +System.lineSeparator() +
                         "    \"dict-item-2\" : \"thing\"" +System.lineSeparator() +
                         "  }," +System.lineSeparator() +
                         "  \"list\" : [ \"list-item-1\", \"list-item-2\" ]," +System.lineSeparator() +
                         "  \"number\" : 123456," +System.lineSeparator() +
                         "  \"string\" : \"value\"" +System.lineSeparator() +
                         '}', BENCODEJSONTools.bencodeToJson("d4:dictd11:dict-item-14:test11:dict-item-25:thinge4:listl11:list-item-111:list-item-2e6:numberi123456e6:string5:valuee"));

    }

    @Test
    void testBencodeStringWithNewline() {
        // A bencode string containing a newline byte (0x0A)
        // In bencode format: "4:<newline>foo" where <newline> is the actual byte 0x0A
        String bencodeWithNewline = "4:\nfoo";
        String json = BENCODEJSONTools.bencodeToJson(bencodeWithNewline);
        // The JSON should contain the newline as an escape sequence \n
        assertTrue(json.contains("\\n") || json.contains("\n"), "JSON should contain newline: " + json);
    }

    @Test
    void testBencodeStringWithTab() {
        // A bencode string containing a tab byte (0x09)
        String bencodeWithTab = "4:\tfoo";
        String json = BENCODEJSONTools.bencodeToJson(bencodeWithTab);
        // The JSON should contain the tab as an escape sequence \t
        assertTrue(json.contains("\\t") || json.contains("\t"), "JSON should contain tab: " + json);
    }

    @Test
    void testBencodeStringWithBackslash() {
        // A bencode string containing a backslash byte (0x5C)
        String bencodeWithBackslash = "4:\\foo";
        String json = BENCODEJSONTools.bencodeToJson(bencodeWithBackslash);
        // The result should be the raw string with a backslash
        assertEquals("\\foo", json, "Should return string with backslash");
    }

    @Test
    void testJsonToBencodeRoundtripWithNewline() {
        // JSON string with newline escape
        String jsonInput = "\"hello\\nworld\"";
        String bencode = BENCODEJSONTools.jsonToBencode(jsonInput);
        // The bencode should contain the actual newline byte, not the literal \n
        // Bencode format: "11:hello\nworld" (11 bytes: h,e,l,l,o,\n,w,o,r,l,d)
        String jsonOutput = BENCODEJSONTools.bencodeToJson(bencode);
        assertEquals("hello\nworld", jsonOutput, "Roundtrip should preserve newline");
    }

    @Test
    void testJsonToBencodeRoundtripWithTab() {
        String jsonInput = "\"hello\\tworld\"";
        String bencode = BENCODEJSONTools.jsonToBencode(jsonInput);
        String jsonOutput = BENCODEJSONTools.bencodeToJson(bencode);
        assertEquals("hello\tworld", jsonOutput, "Roundtrip should preserve tab");
    }

    @Test
    void testJsonToBencodeRoundtripWithBackslash() {
        String jsonInput = "\"hello\\\\world\"";
        String bencode = BENCODEJSONTools.jsonToBencode(jsonInput);
        String jsonOutput = BENCODEJSONTools.bencodeToJson(bencode);
        assertEquals("hello\\world", jsonOutput, "Roundtrip should preserve backslash");
    }

    @Test
    void testJsonToBencodeRoundtripWithQuote() {
        String jsonInput = "\"hello\\\"world\"";
        String bencode = BENCODEJSONTools.jsonToBencode(jsonInput);
        String jsonOutput = BENCODEJSONTools.bencodeToJson(bencode);
        assertEquals("hello\"world", jsonOutput, "Roundtrip should preserve quote");
    }

    @Test
    void testBencodeDictionaryWithNewlineInKey() {
        // A dictionary where a key contains a newline byte
        // "file\nname" is 9 bytes: f,i,l,e,\n,n,a,m,e
        // "test.txt" is 8 bytes
        // Structure: d 4:name 5:Jonas 9:file\nname 8:test.txt e
        String bencode = "d4:name5:Jonas9:file\nname8:test.txte";
        String json = BENCODEJSONTools.bencodeToJson(bencode);
        assertNotNull(json);
        // The key "file\nname" contains a newline - this is valid UTF-8
        assertFalse(json.startsWith("Error:"), "Should not fail: " + json);
    }
}
