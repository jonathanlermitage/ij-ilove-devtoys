package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.ToolSelector;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.PasswordStrengthToolSetup;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PasswordStrengthToolPanel implements ToolPanel {

    public static final String HASH_GENERATOR_TOOL_NAME = "Hash generator";

    private JPanel rootPanel;
    private JTextField passwordStrengthPasswordTextField;
    private JTextArea passwordStrengthReportTextArea;
    private JButton hashItButton;

    public PasswordStrengthToolPanel(ToolSelector toolSelector) {
        new PasswordStrengthToolSetup(
            passwordStrengthPasswordTextField,
            passwordStrengthReportTextArea,
            hashItButton,
            null, // hash input lives in the Hash generator tool now; reached via toolSelector below
            e -> {
                ToolPanel hashPanel = toolSelector.selectTool(HASH_GENERATOR_TOOL_NAME);
                ((HashToolPanel) hashPanel).applyInput(passwordStrengthPasswordTextField.getText());
            }).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
