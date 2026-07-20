package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.PasswordVerifierToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PasswordVerifierToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JTextField passwordVerifierInputPassword;
    private JTextField passwordVerifierHashTextField;
    private JBTextField passwordVerifierResultLabel;

    public PasswordVerifierToolPanel() {
        new PasswordVerifierToolSetup(
            passwordVerifierHashTextField,
            passwordVerifierInputPassword,
            passwordVerifierResultLabel).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>Type a password and a hash<br>" +
            "and the tool will say if the password<br>" +
            "verifies the hash with an algorithm like MD5,<br>" +
            "SHA1/256/384/512 or BCrypt 2A/2B/2Y.</html>";
    }
}
