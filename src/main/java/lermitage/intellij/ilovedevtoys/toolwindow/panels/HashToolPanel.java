package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.HashToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HashToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JTextArea hashInputTextArea;
    private JBTextField hashMD5TextField;
    private JBTextField hashSHA1TextField;
    private JBTextField hashSHA256TextField;
    private JBTextField hashSHA384TextField;
    private JBTextField hashSHA512TextField;
    private JBTextField hashBCrypt2ATextField;
    private JBTextField hashBCrypt2BTextField;
    private JBTextField hashBCrypt2YTextField;

    private final HashToolSetup hashToolSetup;

    public HashToolPanel() {
        hashToolSetup = new HashToolSetup(
            hashInputTextArea,
            hashMD5TextField,
            hashSHA1TextField,
            hashSHA256TextField,
            hashSHA384TextField,
            hashSHA512TextField,
            hashBCrypt2ATextField,
            hashBCrypt2BTextField,
            hashBCrypt2YTextField);
        hashToolSetup.setup();
    }

    /** Feeds text into the hash input and recomputes every hash. Used by other tools (e.g. password strength). */
    public void applyInput(String input) {
        hashInputTextArea.setText(input);
        hashToolSetup.update();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>" +
            "Type text and various hash values will<br>" +
            "be automatically computed as you type.</html>";
    }
}
