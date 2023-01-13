package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.HMACTools;
import org.apache.commons.codec.digest.HmacAlgorithms;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HMACToolSetup extends AbstractToolSetup {

    private final JComboBox<String> hmacAlgoComboBox;
    private final JTextField hmacKeyTextField;
    private final JTextArea hmacInputTextArea;
    private final JTextField hmacResultTextField;

    public HMACToolSetup(JComboBox<String> hmacAlgoComboBox,
                         JTextField hmacKeyTextField,
                         JTextArea hmacInputTextArea,
                         JTextField hmacResultTextField) {
        this.hmacAlgoComboBox = hmacAlgoComboBox;
        this.hmacKeyTextField = hmacKeyTextField;
        this.hmacInputTextArea = hmacInputTextArea;
        this.hmacResultTextField = hmacResultTextField;
    }

    public void setup() {
        for (HmacAlgorithms algorithm : HmacAlgorithms.values()) {
            hmacAlgoComboBox.addItem(algorithm.getName());
        }
        hmacAlgoComboBox.setSelectedIndex(0);
        hmacAlgoComboBox.addActionListener(e -> updateResult());
        hmacKeyTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateResult();
            }
        });
        hmacInputTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateResult();
            }
        });
    }

    private void updateResult() {
        hmacResultTextField.setText(HMACTools.generateHMAC(
            HmacAlgorithms.values()[hmacAlgoComboBox.getSelectedIndex()],
            hmacInputTextArea.getText(),
            hmacKeyTextField.getText()));
    }
}
