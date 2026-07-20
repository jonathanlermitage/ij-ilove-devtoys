package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.JSONYAMLToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JSONYAMLToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JTextArea jsonyamlJSONTextArea;
    private JTextArea jsonyamlYAMLTextArea;

    public JSONYAMLToolPanel() {
        new JSONYAMLToolSetup(
            jsonyamlJSONTextArea,
            jsonyamlYAMLTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>" +
            "Type some JSON or YAML and it will be<br>" +
            "automatically converted as you type.</html>";
    }
}
