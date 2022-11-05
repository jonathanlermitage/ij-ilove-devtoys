package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.ASCIIHEXTools;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ASCIIHEXToolSetup {

    private final JTextArea asciihexASCIITextArea;
    private final JTextArea asciihexHEXTextArea;
    private final JCheckBox asciihexSpacesCheckBox;

    public ASCIIHEXToolSetup(JTextArea asciihexASCIITextArea,
                             JTextArea asciihexHEXTextArea,
                             JCheckBox asciihexSpacesCheckBox) {
        this.asciihexASCIITextArea = asciihexASCIITextArea;
        this.asciihexHEXTextArea   = asciihexHEXTextArea;
        this.asciihexSpacesCheckBox = asciihexSpacesCheckBox;
    }

    public void setup() {
        asciihexSpacesCheckBox.addActionListener(e -> asciihexHEXTextArea.setText(ASCIIHEXTools.asciiToHex(asciihexASCIITextArea.getText(), asciihexSpacesCheckBox.isSelected())));
        asciihexASCIITextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                asciihexHEXTextArea.setText(ASCIIHEXTools.asciiToHex(asciihexASCIITextArea.getText(), asciihexSpacesCheckBox.isSelected()));
            }
        });
        asciihexHEXTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                asciihexASCIITextArea.setText(ASCIIHEXTools.hexToAscii(asciihexHEXTextArea.getText()));
            }
        });
    }
}
