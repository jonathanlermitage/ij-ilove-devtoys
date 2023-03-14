package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import at.favre.lib.crypto.bcrypt.BCrypt.Version;
import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.tools.HashTools;

import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HashToolSetup extends AbstractToolSetup {

    private final JTextArea hashInputTextArea;
    private final JBTextField hashMD5TextField;
    private final JBTextField hashSHA1TextField;
    private final JBTextField hashSHA256TextField;
    private final JBTextField hashSHA384TextField;
    private final JBTextField hashSHA512TextField;
    private final JBTextField hashBCrypt2ATextField;
    private final JBTextField hashBCrypt2BTextField;
    private final JBTextField hashBCrypt2YTextField;

    public HashToolSetup(JTextArea hashInputTextArea,
                         JBTextField hashMD5TextField,
                         JBTextField hashSHA1TextField,
                         JBTextField hashSHA256TextField,
                         JBTextField hashSHA384TextField,
                         JBTextField hashSHA512TextField,
                         JBTextField hashBCrypt2ATextField,
                         JBTextField hashBCrypt2BTextField,
                         JBTextField hashBCrypt2YTextField) {
        this.hashInputTextArea = hashInputTextArea;
        this.hashMD5TextField = hashMD5TextField;
        this.hashSHA1TextField = hashSHA1TextField;
        this.hashSHA256TextField = hashSHA256TextField;
        this.hashSHA384TextField = hashSHA384TextField;
        this.hashSHA512TextField = hashSHA512TextField;
        this.hashBCrypt2ATextField = hashBCrypt2ATextField;
        this.hashBCrypt2BTextField = hashBCrypt2BTextField;
        this.hashBCrypt2YTextField = hashBCrypt2YTextField;
    }

    public void setup() {
        hashInputTextArea.setToolTipText("Nota: hash outputs type is Hex, not Base64.");
        hashInputTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String input = hashInputTextArea.getText();
                hashMD5TextField.setText(HashTools.generateMD5(input));
                hashSHA1TextField.setText(HashTools.generateSHA1(input));
                hashSHA256TextField.setText(HashTools.generateSHA256(input));
                hashSHA384TextField.setText(HashTools.generateSHA384(input));
                hashSHA512TextField.setText(HashTools.generateSHA512(input));
                hashBCrypt2ATextField.setText(HashTools.generateBCrypt(input, Version.VERSION_2A));
                hashBCrypt2BTextField.setText(HashTools.generateBCrypt(input, Version.VERSION_2B));
                hashBCrypt2YTextField.setText(HashTools.generateBCrypt(input, Version.VERSION_2Y));
            }
        });
    }
}
