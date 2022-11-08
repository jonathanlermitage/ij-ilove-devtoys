package lermitage.intellij.ilovedevtoys.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONStringToolsTest {

    @Test
    void should_jsonToString_convert_minimal_string() {
        assertEquals(
            "\"{\\\"browsers\\\":2}\"",
            JSONStringTools.jsonToString("{\"browsers\": 2}"));
    }

    @Test
    void should_jsonToString_convert_simple_string() {
        assertEquals(
            "\"{\\\"browsers\\\":\\\"1\\\\\\\\2\\\"}\"",
            JSONStringTools.jsonToString("{\"browsers\": \"1\\\\2\"}"));
    }

    @Test
    void should_jsonToString_convert_complex_string() {
        assertEquals(
            "\"{\\\"browsers\\\":{\\\"firefox\\\":{\\\"name\\\":\\\"Firefox\\\\\\\\\\\",\\\"pref_url\\\":\\\"about:config\\\",\\\"releases\\\":{\\\"1\\\":{\\\"release_date\\\":\\\"2004-11-09\\\",\\\"status\\\":\\\"retired\\\",\\\"engine\\\":\\\"Gecko\\\",\\\"engine_version\\\":\\\"1.7\\\"}}}}}\"",
            JSONStringTools.jsonToString("""
                {
                  "browsers": {
                    "firefox": {
                      "name": "Firefox\\\\",
                      "pref_url": "about:config",
                      "releases": {
                        "1": {
                          "release_date": "2004-11-09",
                          "status": "retired",
                          "engine": "Gecko",
                          "engine_version": "1.7"
                        }
                      }
                    }
                  }
                }
                """));
    }
}
