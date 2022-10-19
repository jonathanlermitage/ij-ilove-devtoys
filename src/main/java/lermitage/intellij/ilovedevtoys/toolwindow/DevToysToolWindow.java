package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.openapi.wm.ToolWindow;
import lermitage.intellij.ilovedevtoys.tools.Base64Tools;

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

    private final LinkedHashMap<String, JPanel> toolPanelsByTitle = new LinkedHashMap<>();

    public DevToysToolWindow(ToolWindow toolWindow) {
        toolPanelsByTitle.put("Base64 encoder/decoder", base64Panel);
        toolPanelsByTitle.put("URL encoder/decoder", urlCodecPanel);
        toolPanelsByTitle.put("Lorem Ipsum generator", loremIpsumPanel);
        toolPanelsByTitle.put("Hash generator", hashPanel);

        setupBase64Tool();

        toolPanelsByTitle.forEach((s, jPanel) -> toolComboBox.addItem(s));
        toolComboBox.addActionListener(e -> displayToolPanel(toolComboBox.getItemAt(toolComboBox.getSelectedIndex())));
        toolComboBox.setSelectedIndex(0);
    }

    private void hideToolPanels() {
        base64Panel.setVisible(false);
        urlCodecPanel.setVisible(false);
        loremIpsumPanel.setVisible(false);
        hashPanel.setVisible(false);
    }

    private void displayToolPanel(String toolPanelTitle) {
        hideToolPanels();
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
}
