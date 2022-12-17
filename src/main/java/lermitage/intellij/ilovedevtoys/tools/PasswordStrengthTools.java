package lermitage.intellij.ilovedevtoys.tools;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

public class PasswordStrengthTools {

    public static String getStrength(String pwd) {
        try {
            if (pwd.isEmpty()) {
                return "";
            }
            Zxcvbn zxcvbn = new Zxcvbn();
            Strength strength = zxcvbn.measure(pwd);
            String report = "- score: " + scoreToString(strength.getScore()) + "\n";
            report += "- guesses: " + strength.getGuesses() + "\n";
            report += "- guessesLog10: " + strength.getGuessesLog10() + "\n";
            report += "- crackTimeSeconds:\n";
            report += "  - onlineThrottling100PerHour: " + strength.getCrackTimeSeconds().getOnlineThrottling100perHour() + "\n";
            report += "  - onlineNoThrottling10PerSecond: " + strength.getCrackTimeSeconds().getOnlineNoThrottling10perSecond() + "\n";
            report += "  - offlineSlowHashing1e4PerSecond: " + strength.getCrackTimeSeconds().getOfflineSlowHashing1e4perSecond() + "\n";
            report += "  - offlineFastHashing1e10PerSecond: " + strength.getCrackTimeSeconds().getOfflineFastHashing1e10PerSecond() + "\n";
            report += "- crackTimeDisplay:\n";
            report += "  - onlineThrottling100PerHour: " + strength.getCrackTimesDisplay().getOnlineThrottling100perHour() + "\n";
            report += "  - onlineNoThrottling10PerSecond: " + strength.getCrackTimesDisplay().getOnlineNoThrottling10perSecond() + "\n";
            report += "  - offlineSlowHashing1e4PerSecond: " + strength.getCrackTimesDisplay().getOfflineSlowHashing1e4perSecond() + "\n";
            report += "  - offlineFastHashing1e10PerSecond: " + strength.getCrackTimesDisplay().getOfflineFastHashing1e10PerSecond() + "\n";
            report += "- feedback: \n";
            report += "  - warning: " + strength.getFeedback().getWarning() + "\n";
            report += "  - suggestions: " + strength.getFeedback().getSuggestions() + "\n";
            report += "- calc_time: " + strength.getCalcTime() + "\n\n";
            report += "Some explanations here: https://github.com/nulab/zxcvbn4j#strength-properties";
            return report;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String scoreToString(int score) {
        if (score == 0) {
            return score + "/4, Weak (guesses < 10^3 + 5) 🤬";
        }
        if (score == 1) {
            return score + "/4, Fair (guesses < 10^6 + 5) 🥵";
        }
        if (score == 2) {
            return score + "/4, Good (guesses < 10^8 + 5) 🤨";
        }
        if (score == 3) {
            return score + "/4, Strong (guesses < 10^10 + 5) 😀";
        }
        if (score == 4) {
            return score + "/4, Very strong (guesses >= 10^10 + 5) 😎";
        }
        return Integer.toString(score);
    }
}
