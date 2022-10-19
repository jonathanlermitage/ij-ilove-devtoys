package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.openapi.wm.ToolWindow;
import lermitage.intellij.ilovedevtoys.tools.Base64Tools;
import lermitage.intellij.ilovedevtoys.tools.HashTools;
import lermitage.intellij.ilovedevtoys.tools.LoremIpsumTools;
import lermitage.intellij.ilovedevtoys.tools.URLTools;
import lermitage.intellij.ilovedevtoys.tools.UUIDTools;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

public class DevToysToolWindow {

    private JPanel mainPanel;
    private JComboBox<String> toolComboBox;

    private JPanel base64Panel;
    private JRadioButton base64RadioButtonUTF8;
    private JRadioButton base64RadioButtonASCII;
    private JTextArea base64RawTextArea;
    private JTextArea base64Base64TextArea;

    private JPanel urlCodecPanel;
    private JTextField urlCodecDecodedTextField;
    private JTextField urlCodecEncodedTextField;

    private JPanel loremIpsumPanel;
    private JButton loremIpsumGenerateButton;
    private JTextArea loremIpsumTextArea;

    private JPanel hashPanel;
    private JTextArea hashInputTextArea;
    private JTextField hashMD5TextField;
    private JTextField hashSHA1TextField;
    private JTextField hashSHA256TextField;
    private JTextField hashSHA512TextField;

    private JPanel uuidPanel;
    private JButton uuidGenerateButton;
    private JTextArea uuidTextArea;

    private final LinkedHashMap<String, JPanel> toolPanelsByTitle = new LinkedHashMap<>();

    public DevToysToolWindow(ToolWindow toolWindow) {
        toolPanelsByTitle.put("Base64 encoder/decoder", base64Panel);
        toolPanelsByTitle.put("URL encoder/decoder", urlCodecPanel);
        toolPanelsByTitle.put("Lorem Ipsum generator", loremIpsumPanel);
        toolPanelsByTitle.put("Hash generator", hashPanel);
        toolPanelsByTitle.put("UUID generator", uuidPanel);

        setupBase64Tool();
        setupURLCodecTools();
        setupLoremIpsumTool();
        setupHashTool();
        setupUUIDTool();

        toolPanelsByTitle.forEach((s, jPanel) -> toolComboBox.addItem(s));
        toolComboBox.addActionListener(e -> displayToolPanel(toolComboBox.getItemAt(toolComboBox.getSelectedIndex())));
        toolComboBox.setSelectedIndex(0);
    }

    private void displayToolPanel(String toolPanelTitle) {
        toolPanelsByTitle.forEach((s, jPanel) -> jPanel.setVisible(false));
        toolPanelsByTitle.get(toolPanelTitle).setVisible(true);
    }

    public JPanel getContent() {
        return mainPanel;
    }

    private void setupBase64Tool() {
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
            }
        });
    }

    private void setupURLCodecTools() {
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

    private void setupLoremIpsumTool() {
        loremIpsumTextArea.setText(LoremIpsumTools.generateLoremIpsum());
        loremIpsumGenerateButton.addActionListener(e -> loremIpsumTextArea.setText(LoremIpsumTools.generateLoremIpsum()));
    }

    private void setupHashTool() {
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
                hashSHA512TextField.setText(HashTools.generateSHA512(input));
            }
        });
    }

    private void setupUUIDTool() {
        uuidTextArea.setText(UUIDTools.generateUUIDs(20));
        uuidGenerateButton.addActionListener(e -> uuidTextArea.setText(UUIDTools.generateUUIDs(20)));
    }
}
