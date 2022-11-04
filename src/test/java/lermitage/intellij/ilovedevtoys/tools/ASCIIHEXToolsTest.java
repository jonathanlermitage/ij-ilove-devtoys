package lermitage.intellij.ilovedevtoys.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ASCIIHEXToolsTest {

    @Test
    void asciiToHex() {
        assertEquals("48656C6C6F20776F726C64", ASCIIHEXTools.asciiToHex("Hello world", false));
    }

    @Test
    void asciiToHexWithSpaces() {
        assertEquals("48 65 6C 6C 6F 20 77 6F 72 6C 64", ASCIIHEXTools.asciiToHex("Hello world", true));
    }

    @Test
    void asciiToHexNonAscii() {
        assertEquals("Error: non ASCII characters", ASCIIHEXTools.asciiToHex("Hello world€", false));
    }

    @Test
    void hexToAscii() {
        assertEquals("Hello world", ASCIIHEXTools.hexToAscii("48656c6c6f20776f726c64"));
    }

    @Test
    void hexToAsciiWithSpaces() {
        assertEquals("Hello world", ASCIIHEXTools.hexToAscii("48 65 6C 6C 6F 20 77 6F 72 6C 64"));
    }
    @Test
    void hexToAsciiOddLength() {
        assertEquals("Hello worl", ASCIIHEXTools.hexToAscii("48656c6c6f20776f726c6"));
    }

    @Test
    void hexToAsciiOddLengthWithSpaces() {
        assertEquals("Hello worl", ASCIIHEXTools.hexToAscii("48 65 6C 6C 6F 20 77 6F 72 6C 6"));
    }
}
