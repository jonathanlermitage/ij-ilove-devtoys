package lermitage.intellij.ilovedevtoys.tools;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_MD5;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_1;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_224;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_384;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_512;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HMACToolsTest {

    private static final String key = "foobar123";
    private static final String input = "LoremIpsum";

    private static Stream<Arguments> HMACDataset() {
        return Stream.of(
            Arguments.of(HMAC_MD5, "8de696e2b682b54b6c856b72bbb32d1d"),
            Arguments.of(HMAC_SHA_1, "24e41d0483c5a11beadc37fec272c4e8e16ac5bc"),
            Arguments.of(HMAC_SHA_224, "c57bdc1d47c27d9969cb191c7ca381aa30b9d3c74172da7da990d501"),
            Arguments.of(HMAC_SHA_256, "e6097534366b0ecc65efab76dba9f2b0ee3042871d68bcc03dc993b0a32545cd"),
            Arguments.of(HMAC_SHA_384, "b68a9661bd3ff88c5c2fc0f7b8d9adf0fe6bbbb8dc945ce672930e563b4834b9e3c7fcab0a8b54b254e1a26689901455"),
            Arguments.of(HMAC_SHA_512, "66349ffa2c9ee6a09c0c6330a22a60a85b079a50089bd4789706c4bf830cfb2c9a4dbd3132a4f7782fccb10120adf2cf3cb9e282d821442edce05221bbc770b1")
        );
    }

    @ParameterizedTest
    @MethodSource("HMACDataset")
    void generateHMAC(HmacAlgorithms algorithm, String expected) {
        assertEquals(expected, HMACTools.generateHMAC(algorithm, input, key));
    }

    @Test
    void generateEmpty() {
        assertEquals("", HMACTools.generateHMAC(HMAC_MD5, "", key));
    }
}
