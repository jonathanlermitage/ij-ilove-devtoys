package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.LoremIpsumToolSetup;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LoremIpsumToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JButton loremIpsumGenerateButton;
    private JTextArea loremIpsumTextArea;

    public LoremIpsumToolPanel() {
        new LoremIpsumToolSetup(
            loremIpsumGenerateButton,
            loremIpsumTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
