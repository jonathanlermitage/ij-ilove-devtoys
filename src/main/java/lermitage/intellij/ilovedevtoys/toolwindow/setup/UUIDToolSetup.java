package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.UUIDTools;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class UUIDToolSetup {

    private final JButton uuidGenerateButton;
    private final JTextArea uuidTextArea;

    public UUIDToolSetup(JButton uuidGenerateButton,
                         JTextArea uuidTextArea) {
        this.uuidGenerateButton = uuidGenerateButton;
        this.uuidTextArea = uuidTextArea;
    }

    public void setup() {
        uuidTextArea.setText(UUIDTools.generateUUIDs(20));
        uuidGenerateButton.addActionListener(e -> uuidTextArea.setText(UUIDTools.generateUUIDs(20)));
    }
}
