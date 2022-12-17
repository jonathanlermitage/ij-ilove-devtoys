package lermitage.intellij.ilovedevtoys.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordStrengthToolsTest {

    @Test
    void getEmptyPasswordStrength() {
        assertEquals("", PasswordStrengthTools.getStrength(""));
    }

    @Test
    void getWeakPasswordStrength() {
        assertTrue(PasswordStrengthTools.getStrength("12345")
            .contains("score: 0/4, Weak"));
    }

    @Test
    void getStrongPasswordStrength() {
        assertTrue(PasswordStrengthTools.getStrength("@'n+c3gtd$Zs!*Pnp*l\"$K^=/}%@KmkI!#OFG2FM:`H<Jif.M?")
            .contains("score: 4/4, Very strong"));
    }
}
