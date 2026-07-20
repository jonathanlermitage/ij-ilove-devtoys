package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.EscapeToolSetup;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class EscapeToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JComboBox<String> escapeComboBox;
    private JTextArea unescapedTextArea;
    private JTextArea escapedTextArea;

    public EscapeToolPanel() {
        new EscapeToolSetup(
            escapeComboBox,
            unescapedTextArea,
            escapedTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
