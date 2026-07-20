package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.HMACToolSetup;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HMACToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JComboBox<String> hmacAlgoComboBox;
    private JTextField hmacKeyTextField;
    private JTextArea hmacInputTextArea;
    private JTextField hmacResultTextField;

    public HMACToolPanel() {
        new HMACToolSetup(
            hmacAlgoComboBox,
            hmacKeyTextField,
            hmacInputTextArea,
            hmacResultTextField).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
