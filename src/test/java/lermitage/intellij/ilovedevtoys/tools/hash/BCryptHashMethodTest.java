package lermitage.intellij.ilovedevtoys.tools.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BCryptHashMethodTest {

    @Test
    void match2A() {
        assertTrue(new BCryptHashMethod(BCryptVersion.BCryptVersion2a).match("Hello world", "$2a$06$5qUvWATML0qkct/t7c1aTuF7i0QRH5LzKGF4N9go0Merm4WW/XeHK"));
    }
    @Test
    void match2B() {
        assertTrue(new BCryptHashMethod(BCryptVersion.BCryptVersion2b).match("Hello world", "$2b$06$8HCWp2IMg9m6gx3ST12uNOWKFesdyF3cNYqcWR6yoLlFZWcxF8Hl2"));
    }

    @Test
    void match2Y() {
        assertTrue(new BCryptHashMethod(BCryptVersion.BCryptVersion2y).match("Hello world", "$2y$06$V8TA7dpf50cFl8FNhnLNv.ZnZNy2MiHaEHCNSSz6Odb0PemGsWgh6"));
    }

}
