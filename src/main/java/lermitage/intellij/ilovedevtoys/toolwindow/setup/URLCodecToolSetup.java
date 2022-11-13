package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.tools.URLTools;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class URLCodecToolSetup extends AbstractToolSetup {

    private final JBTextField urlCodecDecodedTextField;
    private final JBTextField urlCodecEncodedTextField;

    public URLCodecToolSetup(JBTextField urlCodecDecodedTextField,
                             JBTextField urlCodecEncodedTextField) {
        this.urlCodecDecodedTextField = urlCodecDecodedTextField;
        this.urlCodecEncodedTextField = urlCodecEncodedTextField;
    }

    public void setup() {
        urlCodecDecodedTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                urlCodecEncodedTextField.setText(URLTools.encodeURL(urlCodecDecodedTextField.getText()));
            }
        });
        urlCodecEncodedTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                urlCodecDecodedTextField.setText(URLTools.decodeURL(urlCodecEncodedTextField.getText()));
            }
        });
    }
}
