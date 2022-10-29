package lermitage.intellij.ilovedevtoys.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class Base64ToolsTest {

    private static Stream<Arguments> encodeUtf8TextToBase64Dataset() {
        return Stream.of(
            Arguments.of("testé", "dGVzdMOp"),
            Arguments.of("", "")
        );
    }

    @ParameterizedTest
    @MethodSource("encodeUtf8TextToBase64Dataset")
    void should_encode_utf8_text_to_base64(String input, String expected) {
        Assertions.assertEquals(expected, Base64Tools.toBase64(input, Base64Tools.UTF_8));
    }

    private static Stream<Arguments> encodeBase64ToUtf8TextDataset() {
        return Stream.of(
            Arguments.of("dGVzdMOp", "testé"),
            Arguments.of("", "")
        );
    }

    @ParameterizedTest
    @MethodSource("encodeBase64ToUtf8TextDataset")
    void should_encode_base64_to_utf8_text(String input, String expected) {
        Assertions.assertEquals(expected, Base64Tools.toText(input, Base64Tools.UTF_8));
    }

    private static Stream<Arguments> encodeAsciiTextToBase64Dataset() {
        return Stream.of(
            Arguments.of("testé", "dGVzdD8="),
            Arguments.of("", "")
        );
    }

    @ParameterizedTest
    @MethodSource("encodeAsciiTextToBase64Dataset")
    void should_encode_ascii_text_to_base64(String input, String expected) {
        Assertions.assertEquals(
            Base64Tools.toBase64(new String(input.getBytes(StandardCharsets.US_ASCII), Base64Tools.US_ASCII), Base64Tools.US_ASCII),
            expected
        );
    }

    private static Stream<Arguments> encodeBase64ToAsciiTextDataset() {
        return Stream.of(
            Arguments.of("dGVzdD8=", "testé"),
            Arguments.of("", "")
        );
    }

    @ParameterizedTest
    @MethodSource("encodeBase64ToAsciiTextDataset")
    void should_encode_base64_to_ascii_text(String input, String expected) {
        Assertions.assertEquals(
            new String(expected.getBytes(StandardCharsets.US_ASCII), Base64Tools.US_ASCII),
            Base64Tools.toText(input, Base64Tools.US_ASCII)
        );
    }

    @Test
    void should_return_error_if_failed_to_encode_to_base64() {
        Assertions.assertTrue(Base64Tools.toText("11111", Base64Tools.UTF_8).startsWith("Error: "));
    }
}
