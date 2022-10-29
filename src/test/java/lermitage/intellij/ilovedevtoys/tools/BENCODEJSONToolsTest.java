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
}
