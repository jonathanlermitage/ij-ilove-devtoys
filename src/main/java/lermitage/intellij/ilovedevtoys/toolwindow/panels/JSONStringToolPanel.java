package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.JSONStringToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JSONStringToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JTextArea jsonStringJsonArea;
    private JTextArea jsonStringStringTextArea;

    public JSONStringToolPanel() {
        new JSONStringToolSetup(
            jsonStringJsonArea,
            jsonStringStringTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>" +
            "Type some JSON and it will be automatically<br>" +
            "converted to String as you type.</html>";
    }
}
