package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.PasswordStrengthTools;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PasswordStrengthToolSetup extends AbstractToolSetup {

    private final JTextField passwordStrengthPasswordTextField;
    private final JTextArea passwordStrengthReportTextArea;
    private final JButton hashItButton;
    private final JTextArea hashInputTextArea;
    private ActionListener hashItActionListener;

    public PasswordStrengthToolSetup(JTextField passwordStrengthPasswordTextField,
                                     JTextArea passwordStrengthReportTextArea,
                                     JButton hashItButton,
                                     JTextArea hashInputTextArea,
                                     ActionListener hashItActionListener) {
        this.passwordStrengthPasswordTextField = passwordStrengthPasswordTextField;
        this.passwordStrengthReportTextArea = passwordStrengthReportTextArea;
        this.hashItButton = hashItButton;
        this.hashInputTextArea = hashInputTextArea;
        this.hashItActionListener = hashItActionListener;
    }

    public void setup() {

        hashItButton.addActionListener(hashItActionListener);
        passwordStrengthPasswordTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                passwordStrengthReportTextArea.setText(PasswordStrengthTools.getStrength(passwordStrengthPasswordTextField.getText()));
            }
        });
    }
}
