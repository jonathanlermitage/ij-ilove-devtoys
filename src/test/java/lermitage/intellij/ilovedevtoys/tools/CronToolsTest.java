package lermitage.intellij.ilovedevtoys.tools;

import com.cronutils.model.CronType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CronToolsTest {


    @Test
    void should_computeLastAndNextDates_cron4j_cron_str() {
        String output = CronTools.explainAndGetDates("0 * */2 * *", CronType.CRON4J, 5);
        assertTrue(output.contains("every hour every 2 days"));
    }

    @Test
    void should_computeLastAndNextDates_quartz_cron_str() {
        String output = CronTools.explainAndGetDates("0 0 * */2 * ? *", CronType.QUARTZ, 5);
        assertTrue(output.contains("every hour every 2 days"));
    }

    @Test
    void should_computeLastAndNextDates_spring_cron_str() {
        String output = CronTools.explainAndGetDates("0 0 * */2 * *", CronType.SPRING53, 5);
        assertTrue(output.contains("every hour every 2 days"));
    }

    @Test
    void should_computeLastAndNextDates_unix_cron_str() {
        String output = CronTools.explainAndGetDates("0 * */2 * *", CronType.UNIX, 5);
        assertTrue(output.contains("every hour every 2 days"));
    }

    @Test
    void should_not_computeLastAndNextDates_bad_cron4j_cron_str() {
        String output = CronTools.explainAndGetDates("***", CronType.CRON4J, 5);
        assertTrue(output.contains("Error: "));
    }

    @Test
    void should_not_computeLastAndNextDates_bad_quartz_cron_str() {
        String output = CronTools.explainAndGetDates("***", CronType.QUARTZ, 5);
        assertTrue(output.contains("Error: "));
    }

    @Test
    void should_not_computeLastAndNextDates_bad_spring_cron_str() {
        String output = CronTools.explainAndGetDates("***", CronType.SPRING53, 5);
        assertTrue(output.contains("Error: "));
    }

    @Test
    void should_not_computeLastAndNextDates_bad_unix_cron_str() {
        String output = CronTools.explainAndGetDates("***", CronType.UNIX, 5);
        assertTrue(output.contains("Error: "));
    }
}
