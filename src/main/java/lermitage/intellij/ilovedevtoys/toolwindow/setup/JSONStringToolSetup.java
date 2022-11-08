package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.JSONStringTools;

import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JSONStringToolSetup {

    private final JTextArea jsonStringJsonArea;
    private final JTextArea jsonStringStringTextArea;

    public JSONStringToolSetup(JTextArea jsonStringJsonArea,
                               JTextArea jsonStringStringTextArea) {
        this.jsonStringJsonArea = jsonStringJsonArea;
        this.jsonStringStringTextArea = jsonStringStringTextArea;
    }

    public void setup() {
        jsonStringJsonArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                jsonStringStringTextArea.setText(JSONStringTools.jsonToString(jsonStringJsonArea.getText()));
            }
        });
    }
}
