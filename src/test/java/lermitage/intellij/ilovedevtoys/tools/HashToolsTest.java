package lermitage.intellij.ilovedevtoys.tools;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.Test;

import static at.favre.lib.crypto.bcrypt.BCrypt.Version.SUPPORTED_VERSIONS;
import static org.junit.jupiter.api.Assertions.*;

class HashToolsTest {

    @Test
    void generateMD5() {
        assertEquals("3e25960a79dbc69b674cd4ec67a72c62", HashTools.generateMD5("Hello world"));
    }

    @Test
    void generateSHA1() {
        assertEquals("7b502c3a1f48c8609ae212cdfb639dee39673f5e", HashTools.generateSHA1("Hello world"));
    }

    @Test
    void generateSHA256() {
        assertEquals("64ec88ca00b268e5ba1a35678a1b5316d212f4f366b2477232534a8aeca37f3c", HashTools.generateSHA256("Hello world"));
    }

    @Test
    void generateSHA384() {
        assertEquals("9203b0c4439fd1e6ae5878866337b7c532acd6d9260150c80318e8ab8c27ce330189f8df94fb890df1d298ff360627e1", HashTools.generateSHA384("Hello world"));
    }

    @Test
    void generateSHA512() {
        assertEquals("b7f783baed8297f0db917462184ff4f08e69c2d5e5f79a942600f9725f58ce1f29c18139bf80b06c0fff2bdd34738452ecf40c488c22a7e3d80cdf6f9c1c0d47", HashTools.generateSHA512("Hello world"));
    }

    @Test
    void generateBCrypt() {
        SUPPORTED_VERSIONS.forEach(v -> {
            String input = "Hello world";
            String hash = HashTools.generateBCrypt(input, v);
            assertTrue(BCrypt.verifyer().verify(input.toCharArray(), hash).verified);
        });
    }
}
