package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.BENCODEJSONTools;

import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BENCODEJSONToolSetup {

    private final JTextArea bencodejsonBENCODETextArea;
    private final JTextArea bencodejsonJSONTextArea;

    public BENCODEJSONToolSetup(JTextArea bencodejsonBENCODETextArea,
                                JTextArea bencodejsonJSONTextArea) {
        this.bencodejsonBENCODETextArea = bencodejsonBENCODETextArea;
        this.bencodejsonJSONTextArea = bencodejsonJSONTextArea;
    }

    public void setup() {
        bencodejsonBENCODETextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                bencodejsonJSONTextArea.setText(BENCODEJSONTools.bencodeToJson(bencodejsonBENCODETextArea.getText()));
            }
        });
        bencodejsonJSONTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                bencodejsonBENCODETextArea.setText(BENCODEJSONTools.jsonToBencode(bencodejsonJSONTextArea.getText()));
            }
        });
    }
}
