package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.UUIDToolSetup;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UUIDToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JButton uuidGenerateButton;
    private JTextArea uuidTextArea;

    public UUIDToolPanel() {
        new UUIDToolSetup(
            uuidGenerateButton,
            uuidTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
