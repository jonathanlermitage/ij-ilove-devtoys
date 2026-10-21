package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.Base64ToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Base64ToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JBRadioButton base64RadioButtonUTF8;
    private JBRadioButton base64RadioButtonASCII;
    private JTextArea base64RawTextArea;
    private JTextArea base64Base64TextArea;
    private JBTextField base64SeparatorTextField;

    public Base64ToolPanel() {
        new Base64ToolSetup(
            base64RadioButtonUTF8,
            base64RadioButtonASCII,
            base64RawTextArea,
            base64Base64TextArea,
            base64SeparatorTextField).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>" +
            "Type some text or Base64 and it will be<br>" +
            "automatically converted as you type.<br>" +
            "Set a separator to convert multiple values at once.</html>";
    }
}
