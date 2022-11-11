package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.intellij.ui.components.JBRadioButton;
import lermitage.intellij.ilovedevtoys.tools.Base64Tools;

import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Base64ToolSetup extends AbstractToolSetup {

    private final JBRadioButton base64RadioButtonUTF8;
    private final JBRadioButton base64RadioButtonASCII;
    private final JTextArea base64RawTextArea;
    private final JTextArea base64Base64TextArea;

    public Base64ToolSetup(JBRadioButton base64RadioButtonUTF8,
                           JBRadioButton base64RadioButtonASCII,
                           JTextArea base64RawTextArea,
                           JTextArea base64Base64TextArea) {
        this.base64RadioButtonUTF8 = base64RadioButtonUTF8;
        this.base64RadioButtonASCII = base64RadioButtonASCII;
        this.base64RawTextArea = base64RawTextArea;
        this.base64Base64TextArea = base64Base64TextArea;
    }

    public void setup() {
        base64RadioButtonUTF8.setSelected(true);
        base64RadioButtonUTF8.setToolTipText("Encoding change applies on Raw text or Base64 update.");
        base64RadioButtonASCII.setToolTipText("Encoding change applies on Raw text or Base64 update.");
        base64RawTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                base64Base64TextArea.setText(Base64Tools.toBase64(
                    base64RawTextArea.getText(),
                    base64RadioButtonUTF8.isSelected() ? Base64Tools.UTF_8 : Base64Tools.US_ASCII)
                );
                base64Base64TextArea.setCaretPosition(0);
                updateWithBestNumberOfRows(base64RawTextArea, base64Base64TextArea);
            }
        });
        base64Base64TextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                base64RawTextArea.setText(Base64Tools.toText(
                    base64Base64TextArea.getText(),
                    base64RadioButtonUTF8.isSelected() ? Base64Tools.UTF_8 : Base64Tools.US_ASCII)
                );
                base64RawTextArea.setCaretPosition(0);
                updateWithBestNumberOfRows(base64RawTextArea, base64Base64TextArea);
            }
        });
    }
}
