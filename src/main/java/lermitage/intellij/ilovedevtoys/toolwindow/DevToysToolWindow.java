package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.LinkedHashMap;

public class DevToysToolWindow {

    private JPanel mainPanel;
    private JComboBox<ComboBoxWithImageItem> toolComboBox;
    private JLabel helpLabel;

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

    private JPanel bencodejsonPanel;
    private JTextArea bencodejsonBENCODETextArea;
    private JTextArea bencodejsonJSONTextArea;

    private JPanel timestampPanel;
    private JComboBox<ComboBoxWithImageItem> timestampTimezoneComboBox;
    private JTextArea timestampTextArea;
    private JSpinner timestampSpinner;
    private JButton timestampNowButton;
    private JButton timestampUpdateFromTimestampButton;
    private JTextField timestampFilterTextField;
    private JButton timestampUpdateFromFieldsButton;
    private JLabel timestampWarningNoZoneIdLabel;
    private JSpinner timestampYearSpinner;
    private JSpinner timestampDaySpinner;
    private JSpinner timestampMonthSpinner;
    private JSpinner timestampHourSpinner;
    private JSpinner timestampMinuteSpinner;
    private JSpinner timestampSecondSpinner;

    private JPanel dataFakerPanel;
    private JComboBox<String> dataFakerGeneratorComboBox;
    private JButton dataFakerGenerateButton;
    private JComboBox<String> dataFakerLocaleComboBox;
    private JTextArea dataFakerTextArea;

    private JPanel setDiffPanel;
    private JButton setDiffCompareButton;
    private JCheckBox setDiffCaseSensitiveCheckBox;
    private JTextArea setDiffTextArea1;
    private JTextArea setDiffTextArea2;
    private JTextArea setDiffResultTextArea;
    private JCheckBox setDiffIgnoreEmptyLinesCheckBox;

    private JPanel asciihexPanel;
    private JTextArea asciihexASCIITextArea;
    private JTextArea asciihexHEXTextArea;
    private JCheckBox asciihexSpacesCheckBox;

    private JPanel escapePanel;
    private JComboBox<String> escapeComboBox;
    private JTextArea unescapedTextArea;
    private JTextArea escapedTextArea;

    private final LinkedHashMap<String, PanelAndIcon> toolPanelsByTitle = new LinkedHashMap<>();

    private record PanelAndIcon(JPanel panel, String icon) {
    }

    public DevToysToolWindow() {
        String iconsPath = "ilovedevtoys/toolicons/";
        toolPanelsByTitle.put("Base64 encoder/decoder", new PanelAndIcon(base64Panel, iconsPath + "Base64EncoderDecoder.svg"));
        toolPanelsByTitle.put("URL encoder/decoder", new PanelAndIcon(urlCodecPanel, iconsPath + "UrlEncoderDecoder.svg"));
        toolPanelsByTitle.put("Fake Data generator", new PanelAndIcon(dataFakerPanel, iconsPath + "DataFaker.svg"));
        toolPanelsByTitle.put("Timestamp converter", new PanelAndIcon(timestampPanel, iconsPath + "Timestamp.svg"));
        toolPanelsByTitle.put("Set Diff viewer", new PanelAndIcon(setDiffPanel, iconsPath + "SetDiff.svg"));
        toolPanelsByTitle.put("Lorem Ipsum generator", new PanelAndIcon(loremIpsumPanel, iconsPath + "LoremIpsumGenerator.svg"));
        toolPanelsByTitle.put("Hash generator", new PanelAndIcon(hashPanel, iconsPath + "HashGenerator.svg"));
        toolPanelsByTitle.put("UUID generator", new PanelAndIcon(uuidPanel, iconsPath + "UuidGenerator.svg"));
        toolPanelsByTitle.put("JSON <> YAML converter", new PanelAndIcon(jsonyamlPanel, iconsPath + "JsonYaml.svg"));
        toolPanelsByTitle.put("BENCODE <> JSON converter", new PanelAndIcon(bencodejsonPanel, iconsPath + "BencodeJson.svg"));
        toolPanelsByTitle.put("ASCII <> HEX converter", new PanelAndIcon(asciihexPanel, iconsPath + "AsciiHex.svg"));
        toolPanelsByTitle.put("CSV/HTML/Java/JS/XML (un)escaper", new PanelAndIcon(escapePanel, iconsPath + "Escaper.svg"));

        new Base64ToolSetup(
            base64RadioButtonUTF8,
            base64RadioButtonASCII,
            base64RawTextArea,
            base64Base64TextArea).setup();
        new URLCodecToolSetup(
            urlCodecDecodedTextField,
            urlCodecEncodedTextField).setup();
        new DataFakerToolSetup(
            dataFakerGeneratorComboBox,
            dataFakerGenerateButton,
            dataFakerLocaleComboBox,
            dataFakerTextArea).setup();
        new TimestampToolSetup(
            timestampTimezoneComboBox,
            timestampTextArea,
            timestampSpinner,
            timestampNowButton,
            timestampUpdateFromTimestampButton,
            timestampFilterTextField,
            timestampUpdateFromFieldsButton,
            timestampWarningNoZoneIdLabel,
            timestampYearSpinner,
            timestampDaySpinner,
            timestampMonthSpinner,
            timestampHourSpinner,
            timestampMinuteSpinner,
            timestampSecondSpinner).setup();
        new SetDiffToolSetup(
            setDiffCompareButton,
            setDiffCaseSensitiveCheckBox,
            setDiffTextArea1,
            setDiffTextArea2,
            setDiffResultTextArea,
            setDiffIgnoreEmptyLinesCheckBox).setup();
        new LoremIpsumToolSetup(
            loremIpsumGenerateButton,
            loremIpsumTextArea).setup();
        new HashToolSetup(
            hashInputTextArea,
            hashMD5TextField,
            hashSHA1TextField,
            hashSHA256TextField,
            hashSHA384TextField,
            hashSHA512TextField).setup();
        new UUIDToolSetup(
            uuidGenerateButton,
            uuidTextArea).setup();
        new JSONYAMLToolSetup(
            jsonyamlJSONTextArea,
            jsonyamlYAMLTextArea).setup();
        new BENCODEJSONToolSetup(
            bencodejsonBENCODETextArea,
            bencodejsonJSONTextArea).setup();
        new ASCIIHEXToolSetup(
            asciihexASCIITextArea,
            asciihexHEXTextArea,
            asciihexSpacesCheckBox).setup();
        new EscapeToolSetup(escapeComboBox,
            unescapedTextArea,
            escapedTextArea).setup();

        toolPanelsByTitle.forEach((title, panelAndIcon) -> toolComboBox.addItem(new ComboBoxWithImageItem(title, panelAndIcon.icon)));
        toolComboBox.setRenderer(new ComboBoxWithImageRenderer());

        helpLabel.setText("");
        helpLabel.setIcon(IconLoader.getIcon(iconsPath + "contextHelp.svg", DevToysToolWindow.class));
        helpLabel.setToolTipText("");
        helpLabel.setVisible(false);

        toolComboBox.addActionListener(e -> {
            ComboBoxWithImageItem item = toolComboBox.getItemAt(toolComboBox.getSelectedIndex());
            displayToolPanel(item.title());

            helpLabel.setVisible(false);
            switch (item.title()) {
                case "Base64 encoder/decoder" -> {
                    helpLabel.setVisible(true);
                    helpLabel.setToolTipText("<html>" +
                        "Type some text or Base64 and it will be<br>" +
                        "automatically converted as you type.</html>");
                }
                case "URL encoder/decoder" -> {
                    helpLabel.setVisible(true);
                    helpLabel.setToolTipText("<html>" +
                        "Type decoded or encoded URL and it will be<br>" +
                        "automatically converted as you type.</html>");
                }
                case "Hash generator" -> {
                    helpLabel.setVisible(true);
                    helpLabel.setToolTipText("<html>" +
                        "Type text and various hash values will<br>" +
                        "be automatically computed as you type.</html>");
                }
                case "JSON <> YAML converter" -> {
                    helpLabel.setVisible(true);
                    helpLabel.setToolTipText("<html>" +
                        "Type some JSON or YAML and it will be<br>" +
                        "automatically converted as you type.</html>");
                }
                case "BENCODE <> JSON converter" -> {
                    helpLabel.setVisible(true);
                    helpLabel.setToolTipText("<html>" +
                        "Type some BENCODE or JSON and it will be<br>" +
                        "automatically converted as you type.</html>");
                }
                case "Timestamp converter" -> {
                    helpLabel.setVisible(true);
                    helpLabel.setToolTipText("<html>" +
                        "Type a timestamp or update datetime field(s)<br>" +
                        "then conversion should happen automatically.<br>" +
                        "<b>Nota</b>: if automatic conversion doesn't work,<br>" +
                        "please hit the <i>Update from timestamp</i> or<br>" +
                        "<i>Update from fields</i> button.</html>");
                }
                case "Set Diff viewer" -> {
                    helpLabel.setVisible(true);
                    helpLabel.setToolTipText("<html>" +
                        "Type some text in Set 1 and Set 2 then hit <i>Compare</i><br>" +
                        "in order to see if some lines exist only in Set 1 or<br>" +
                        "in Set 2.</html>");
                }
            }
        });
        toolComboBox.setSelectedIndex(0);
    }

    private void displayToolPanel(String toolPanelTitle) {
        toolPanelsByTitle.forEach((s, jPanel) -> jPanel.panel().setVisible(false));
        toolPanelsByTitle.get(toolPanelTitle).panel().setVisible(true);
    }

    public JPanel getContent() {
        return mainPanel;
    }
}
