package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.JSONYAMLTools;

import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JSONYAMLToolSetup {

    private final JTextArea jsonyamlJSONTextArea;
    private final JTextArea jsonyamlYAMLTextArea;

    public JSONYAMLToolSetup(JTextArea jsonyamlJSONTextArea,
                             JTextArea jsonyamlYAMLTextArea) {
        this.jsonyamlJSONTextArea = jsonyamlJSONTextArea;
        this.jsonyamlYAMLTextArea = jsonyamlYAMLTextArea;
    }

    public void setup() {
        jsonyamlJSONTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                jsonyamlYAMLTextArea.setText(JSONYAMLTools.jsonToYaml(jsonyamlJSONTextArea.getText()));
            }
        });
        jsonyamlYAMLTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                jsonyamlJSONTextArea.setText(JSONYAMLTools.yamlToJson(jsonyamlYAMLTextArea.getText()));
            }
        });
    }
}
