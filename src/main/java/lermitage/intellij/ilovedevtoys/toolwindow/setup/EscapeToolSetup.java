package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.EscapeTools;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EscapeToolSetup extends AbstractToolSetup {

    private final JComboBox<String> escapeComboBox;
    private final JTextArea unescapedTextArea;
    private final JTextArea escapedTextArea;

    public EscapeToolSetup(JComboBox<String> escapeComboBox,
                           JTextArea unescapedTextArea,
                           JTextArea escapedTextArea) {
        this.escapeComboBox = escapeComboBox;
        this.unescapedTextArea = unescapedTextArea;
        this.escapedTextArea = escapedTextArea;
    }

    public void setup() {
        for (EscapeTools.EscapeType escapeType : EscapeTools.EscapeType.values()) {
            escapeComboBox.addItem(escapeType.name());
        }
        escapeComboBox.setSelectedIndex(1); // HTML escaper is popular
        unescapedTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                escapedTextArea.setText(EscapeTools.escape(
                    unescapedTextArea.getText(),
                    EscapeTools.EscapeType.valueOf((String) escapeComboBox.getSelectedItem())));
                escapedTextArea.setCaretPosition(0);
                updateWithBestNumberOfRows(unescapedTextArea, escapedTextArea);
            }
        });
        escapedTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                unescapedTextArea.setText(EscapeTools.unescape(
                    escapedTextArea.getText(),
                    EscapeTools.EscapeType.valueOf((String) escapeComboBox.getSelectedItem())));
                unescapedTextArea.setCaretPosition(0);
                updateWithBestNumberOfRows(unescapedTextArea, escapedTextArea);
            }
        });
    }
}
