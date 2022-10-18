package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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

        base64RadioButtonUTF8.setSelected(true);

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
}
