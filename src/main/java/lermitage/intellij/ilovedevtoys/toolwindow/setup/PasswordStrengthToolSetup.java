package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.PasswordStrengthTools;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PasswordStrengthToolSetup extends AbstractToolSetup {

    private final JTextField passwordStrengthPasswordTextField;
    private final JTextArea passwordStrengthReportTextArea;

    public PasswordStrengthToolSetup(JTextField passwordStrengthPasswordTextField,
                                     JTextArea passwordStrengthReportTextArea) {
        this.passwordStrengthPasswordTextField = passwordStrengthPasswordTextField;
        this.passwordStrengthReportTextArea = passwordStrengthReportTextArea;
    }

    public void setup() {
        passwordStrengthPasswordTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                passwordStrengthReportTextArea.setText(PasswordStrengthTools.getStrength(passwordStrengthPasswordTextField.getText()));
            }
        });
    }
}
