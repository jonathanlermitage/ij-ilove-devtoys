package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.tools.HashTools;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PasswordVerifierToolSetup extends AbstractToolSetup {

    private final JTextField passwordVerifierInputPassword;
    private final JTextField passwordVerifierHashTextField;
    private final JBTextField passwordVerifierResultLabel;

    public PasswordVerifierToolSetup(JTextField passwordVerifierHashTextField,
                                     JTextField passwordVerifierInputPassword,
                                     JBTextField passwordVerifierResultLabel) {

        this.passwordVerifierHashTextField = passwordVerifierHashTextField;
        this.passwordVerifierResultLabel = passwordVerifierResultLabel;
        this.passwordVerifierInputPassword = passwordVerifierInputPassword;
    }

    public void setup() {
        var myKeyListener = new MyKeyListener();
        myKeyListener.keyReleased(null);
        passwordVerifierInputPassword.addKeyListener(myKeyListener);
        passwordVerifierHashTextField.addKeyListener(myKeyListener);
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            String input = passwordVerifierInputPassword.getText();
            String hash = passwordVerifierHashTextField.getText();
            passwordVerifierResultLabel.setText(HashTools.verifyPassword(input, hash));
        }
    }
}
