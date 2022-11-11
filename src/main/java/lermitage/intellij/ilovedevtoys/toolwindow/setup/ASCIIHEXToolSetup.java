package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.ASCIIHEXTools;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ASCIIHEXToolSetup extends AbstractToolSetup {

    private final JTextArea asciihexASCIITextArea;
    private final JTextArea asciihexHEXTextArea;
    private final JCheckBox asciihexSpacesCheckBox;

    public ASCIIHEXToolSetup(JTextArea asciihexASCIITextArea,
                             JTextArea asciihexHEXTextArea,
                             JCheckBox asciihexSpacesCheckBox) {
        this.asciihexASCIITextArea = asciihexASCIITextArea;
        this.asciihexHEXTextArea = asciihexHEXTextArea;
        this.asciihexSpacesCheckBox = asciihexSpacesCheckBox;
    }

    public void setup() {
        asciihexSpacesCheckBox.addActionListener(e -> asciihexHEXTextArea.setText(ASCIIHEXTools.asciiToHex(asciihexASCIITextArea.getText(), asciihexSpacesCheckBox.isSelected())));
        asciihexASCIITextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                asciihexHEXTextArea.setText(ASCIIHEXTools.asciiToHex(asciihexASCIITextArea.getText(), asciihexSpacesCheckBox.isSelected()));
                updateWithBestNumberOfRows(asciihexASCIITextArea, asciihexHEXTextArea);
                asciihexHEXTextArea.setCaretPosition(0);
            }
        });
        asciihexHEXTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                asciihexASCIITextArea.setText(ASCIIHEXTools.hexToAscii(asciihexHEXTextArea.getText()));
                updateWithBestNumberOfRows(asciihexASCIITextArea, asciihexHEXTextArea);
                asciihexASCIITextArea.setCaretPosition(0);
            }
        });
    }
}
