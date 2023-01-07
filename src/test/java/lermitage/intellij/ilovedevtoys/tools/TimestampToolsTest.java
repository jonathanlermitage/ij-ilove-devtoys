package lermitage.intellij.ilovedevtoys.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimestampToolsTest {

    @Test
    void should_return_now_as_timestamp() {
        Assertions.assertTrue(TimestampTools.getNowAsTimestampSec() > 0);
    }

    @Test
    void should_return_timestamp_as_human_date() {
        Assertions.assertFalse(TimestampTools.getTimeStampAsHumanDatetime(1667016916L, "America/Montreal", true).contains("1970"));
    }

    @Test
    void should_explode_timestamp_sec() {
        Assertions.assertNotEquals(1970, TimestampTools.toTimestampFields(1667016916L, true).year());
    }

    @Test
    void should_explode_timestamp_ms() {
        Assertions.assertNotEquals(1970, TimestampTools.toTimestampFields(1667016916123L, true).year());
    }
}
