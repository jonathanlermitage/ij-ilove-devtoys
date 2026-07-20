package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.ASCIIHEXToolSetup;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ASCIIHEXToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JTextArea asciihexASCIITextArea;
    private JTextArea asciihexHEXTextArea;
    private JCheckBox asciihexSpacesCheckBox;

    public ASCIIHEXToolPanel() {
        new ASCIIHEXToolSetup(
            asciihexASCIITextArea,
            asciihexHEXTextArea,
            asciihexSpacesCheckBox).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
