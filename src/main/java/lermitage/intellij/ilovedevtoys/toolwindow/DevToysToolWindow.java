package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.tools.Base64Tools;
import lermitage.intellij.ilovedevtoys.tools.HashTools;
import lermitage.intellij.ilovedevtoys.tools.JSONYAMLTools;
import lermitage.intellij.ilovedevtoys.tools.LoremIpsumTools;
import lermitage.intellij.ilovedevtoys.tools.URLTools;
import lermitage.intellij.ilovedevtoys.tools.UUIDTools;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Objects;

public class DevToysToolWindow {

    private JPanel mainPanel;
    private JComboBox<ImageIcon> toolComboBox;

    private JPanel base64Panel;
    private JBRadioButton base64RadioButtonUTF8;
    private JBRadioButton base64RadioButtonASCII;
    private JTextArea base64RawTextArea;
    private JTextArea base64Base64TextArea;

    private JPanel urlCodecPanel;
    private JBTextField urlCodecDecodedTextField;
    private JBTextField urlCodecEncodedTextField;

    private JPanel loremIpsumPanel;
    private JButton loremIpsumGenerateButton;
    private JTextArea loremIpsumTextArea;

    private JPanel hashPanel;
    private JTextArea hashInputTextArea;
    private JBTextField hashMD5TextField;
    private JBTextField hashSHA1TextField;
    private JBTextField hashSHA256TextField;
    private JBTextField hashSHA384TextField;
    private JBTextField hashSHA512TextField;

    private JPanel uuidPanel;
    private JButton uuidGenerateButton;
    private JTextArea uuidTextArea;

    private JPanel jsonyamlPanel;
    private JTextArea jsonyamlJSONTextArea;
    private JTextArea jsonyamlYAMLTextArea;

    private final LinkedHashMap<String, ToolBoxItem> toolPanelsByTitle = new LinkedHashMap<>();

    private record ToolBoxItem(JPanel panel, String iconPath) {
    }

    public DevToysToolWindow() {
        toolPanelsByTitle.put("Base64 encoder/decoder", new ToolBoxItem(base64Panel, "ilovedevtoys/tool_base64.png"));
        toolPanelsByTitle.put("URL encoder/decoder", new ToolBoxItem(urlCodecPanel, "ilovedevtoys/tool_url.png"));
        toolPanelsByTitle.put("Lorem Ipsum generator", new ToolBoxItem(loremIpsumPanel, "ilovedevtoys/tool_loremipsum.png"));
        toolPanelsByTitle.put("Hash generator", new ToolBoxItem(hashPanel, "ilovedevtoys/tool_hash.png"));
        toolPanelsByTitle.put("UUID generator", new ToolBoxItem(uuidPanel, "ilovedevtoys/tool_uuid.png"));
        toolPanelsByTitle.put("JSON <> YAML converter", new ToolBoxItem(jsonyamlPanel, "ilovedevtoys/tool_jsonyaml.png"));

        setupBase64Tool();
        setupURLCodecTools();
        setupLoremIpsumTool();
        setupHashTool();
        setupUUIDTool();
        setupJSONYAMLTool();

        ClassLoader classLoader = DevToysToolWindow.class.getClassLoader();
        toolPanelsByTitle.forEach((s, toolBoxItem) -> {
            ImageIcon langImg = new ImageIcon(Objects.requireNonNull(classLoader.getResource(toolBoxItem.iconPath)));
            langImg.setDescription(s);
            toolComboBox.addItem(langImg);
        });
        toolComboBox.setRenderer(new ComboBoxWithImageRenderer<>());
        toolComboBox.addActionListener(e -> displayToolPanel(toolComboBox.getItemAt(toolComboBox.getSelectedIndex()).getDescription()));
        toolComboBox.setSelectedIndex(0);
    }

    private void displayToolPanel(String toolPanelTitle) {
        toolPanelsByTitle.forEach((s, jPanel) -> jPanel.panel().setVisible(false));
        toolPanelsByTitle.get(toolPanelTitle).panel().setVisible(true);
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
        loremIpsumTextArea.setText(LoremIpsumTools.generateLoremIpsum(200));
        loremIpsumGenerateButton.addActionListener(e -> loremIpsumTextArea.setText(LoremIpsumTools.generateLoremIpsum(200)));
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
                hashSHA384TextField.setText(HashTools.generateSHA384(input));
                hashSHA512TextField.setText(HashTools.generateSHA512(input));
            }
        });
    }

    private void setupUUIDTool() {
        uuidTextArea.setText(UUIDTools.generateUUIDs(20));
        uuidGenerateButton.addActionListener(e -> uuidTextArea.setText(UUIDTools.generateUUIDs(20)));
    }

    private void setupJSONYAMLTool() {
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
