package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.tools.BENCODEJSONTools;
import lermitage.intellij.ilovedevtoys.tools.Base64Tools;
import lermitage.intellij.ilovedevtoys.tools.HashTools;
import lermitage.intellij.ilovedevtoys.tools.JSONYAMLTools;
import lermitage.intellij.ilovedevtoys.tools.LoremIpsumTools;
import lermitage.intellij.ilovedevtoys.tools.TimestampTools;
import lermitage.intellij.ilovedevtoys.tools.URLTools;
import lermitage.intellij.ilovedevtoys.tools.UUIDTools;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DevToysToolWindow {

    private JPanel mainPanel;
    private JComboBox<ComboBoxWithImageItem> toolComboBox;

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
    private JTextArea bencodejsonBENCODETextArea;
    private JPanel bencodejsonPanel;
    private JTextArea bencodejsonJSONTextArea;

    private JPanel timestampPanel;
    private JComboBox<ComboBoxWithImageItem> timestampTimezoneComboBox;
    private JTextArea timestampTextArea;
    private JSpinner timestampYearSpinner;
    private JSpinner timestampDaySpinner;
    private JSpinner timestampMonthSpinner;
    private JSpinner timestampHourSpinner;
    private JSpinner timestampMinuteSpinner;
    private JSpinner timestampSecondSpinner;
    private JSpinner timestampSpinner;
    private JButton timestampNowButton;

    private final LinkedHashMap<String, ToolBoxItem> toolPanelsByTitle = new LinkedHashMap<>();

    // TimestampTool: used to avoid infinitive loops on timestamp spinners update (main spinner
    // updates detailed spinners (year, month... second spinners), and detailed spinners update main spinner)
    private boolean timestampUpdateTriggeredByCode = false;

    private record ToolBoxItem(JPanel panel, String toolIconName) {
    }

    public DevToysToolWindow() {
        String iconsPath = "ilovedevtoys/toolicons/";
        toolPanelsByTitle.put("Base64 encoder/decoder", new ToolBoxItem(base64Panel, iconsPath + "Base64EncoderDecoder.svg"));
        toolPanelsByTitle.put("URL encoder/decoder", new ToolBoxItem(urlCodecPanel, iconsPath + "UrlEncoderDecoder.svg"));
        toolPanelsByTitle.put("Lorem Ipsum generator", new ToolBoxItem(loremIpsumPanel, iconsPath + "LoremIpsumGenerator.svg"));
        toolPanelsByTitle.put("Hash generator", new ToolBoxItem(hashPanel, iconsPath + "HashGenerator.svg"));
        toolPanelsByTitle.put("UUID generator", new ToolBoxItem(uuidPanel, iconsPath + "UuidGenerator.svg"));
        toolPanelsByTitle.put("JSON <> YAML converter", new ToolBoxItem(jsonyamlPanel, iconsPath + "JsonYaml.svg"));
        toolPanelsByTitle.put("BENCODE <> JSON converter", new ToolBoxItem(bencodejsonPanel, iconsPath + "BencodeJson.svg"));
        toolPanelsByTitle.put("Timestamp converter", new ToolBoxItem(timestampPanel, iconsPath + "Timestamp.svg"));

        setupBase64Tool();
        setupURLCodecTools();
        setupLoremIpsumTool();
        setupHashTool();
        setupUUIDTool();
        setupJSONYAMLTool();
        setupBENCODEJSONTool();
        setupTimestampTool();
        setupDataFakerTool();

        toolPanelsByTitle.forEach((s, toolBoxItem) -> {
            toolComboBox.addItem(new ComboBoxWithImageItem(s, toolBoxItem.toolIconName));
        });
        toolComboBox.setRenderer(new ComboBoxWithImageRenderer());
        toolComboBox.addActionListener(e -> {
            ComboBoxWithImageItem item = (ComboBoxWithImageItem) toolComboBox.getItemAt(toolComboBox.getSelectedIndex());
            displayToolPanel(item.title());
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

    private void setupBENCODEJSONTool() {
        bencodejsonBENCODETextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                bencodejsonJSONTextArea.setText(BENCODEJSONTools.bencodeToJson(bencodejsonBENCODETextArea.getText()));
            }
        });
        bencodejsonJSONTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                bencodejsonBENCODETextArea.setText(BENCODEJSONTools.jsonToBencode(bencodejsonJSONTextArea.getText()));
            }
        });
    }

    private void setupTimestampTool() {
        timestampTimezoneComboBox.setRenderer(new ComboBoxWithImageRenderer());

        // populate the ZoneId selector
        Map<String, String> zoneIdesAndFlags = TimestampTools.getAllAvailableZoneIdesAndFlags();
        List<String> zoneIds = zoneIdesAndFlags.keySet().stream()
            .sorted(Comparator.comparing(String::toUpperCase)).toList();
        zoneIds.forEach(zoneId -> {
            String flag = zoneIdesAndFlags.get(zoneId);
            if (flag == null) {
                flag = "_null";
            }
            timestampTimezoneComboBox.addItem(new ComboBoxWithImageItem(
                zoneId, "ilovedevtoys/flags/" + flag + ".svg"));
        });

        // select default ZoneId in selector
        String defaultZoneIdAsStr = ZoneId.systemDefault().toString();
        for (int i = 0; i < timestampTimezoneComboBox.getItemCount(); i++) {
            ComboBoxWithImageItem comboBoxWithImageItem = timestampTimezoneComboBox.getItemAt(i);
            if (comboBoxWithImageItem.title().equalsIgnoreCase(defaultZoneIdAsStr)) {
                timestampTimezoneComboBox.setSelectedIndex(i);
                break;
            }
        }

        long now = TimestampTools.getNowAsTimestamp();
        timestampSpinner.setModel(new SpinnerNumberModel(now, 0L, 9999999999L, 1D));
        timestampSpinner.setEditor(new JSpinner.NumberEditor(timestampSpinner, "#"));
        timestampSpinner.setValue(now);

        timestampYearSpinner.setEditor(new JSpinner.NumberEditor(timestampYearSpinner, "#"));
        timestampMonthSpinner.setEditor(new JSpinner.NumberEditor(timestampMonthSpinner, "#"));
        timestampDaySpinner.setEditor(new JSpinner.NumberEditor(timestampDaySpinner, "#"));
        timestampHourSpinner.setEditor(new JSpinner.NumberEditor(timestampHourSpinner, "#"));
        timestampMinuteSpinner.setEditor(new JSpinner.NumberEditor(timestampMinuteSpinner, "#"));
        timestampSecondSpinner.setEditor(new JSpinner.NumberEditor(timestampSecondSpinner, "#"));

        updateTimestampToolOnTimestampSpinnerUpdate();

        timestampNowButton.addActionListener(e -> timestampSpinner.setValue(TimestampTools.getNowAsTimestamp()));

        KeyListener timestampSpinnerListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateTimestampToolOnTimestampSpinnerUpdate();
            }
        };

        timestampTimezoneComboBox.addKeyListener(timestampSpinnerListener);
        timestampSpinner.addChangeListener(e -> updateTimestampToolOnTimestampSpinnerUpdate());

        timestampYearSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampMonthSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampDaySpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampHourSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampMinuteSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampSecondSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
    }

    private void updateTimestampToolOnTimestampSpinnerUpdate() {
        if (timestampUpdateTriggeredByCode) {
            timestampUpdateTriggeredByCode = false;
            return;
        }
        try {
            timestampUpdateTriggeredByCode = true;
            long spinnerLongValue = getTimestampFieldSpinnerValue(timestampSpinner);
            TimestampTools.TimestampFields timestampFields = TimestampTools.toTimestampFields(spinnerLongValue);
            timestampYearSpinner.setValue(timestampFields.year());
            timestampMonthSpinner.setValue(timestampFields.month());
            timestampDaySpinner.setValue(timestampFields.day());
            timestampHourSpinner.setValue(timestampFields.hours());
            timestampMinuteSpinner.setValue(timestampFields.minutes());
            timestampSecondSpinner.setValue(timestampFields.seconds());
            timestampTextArea.setText(TimestampTools.getTimeStampAsHumanDatetime(spinnerLongValue, getTimestampSelectedZoneIdAsStr()));
        } catch (Exception e) {
            timestampTextArea.setText("Error: " + e.getMessage());
        }
    }

    private void updateTimestampToolOnTimestampFieldsUpdate() {
        if (timestampUpdateTriggeredByCode) {
            timestampUpdateTriggeredByCode = false;
            return;
        }
        try {
            timestampUpdateTriggeredByCode = true;
            TimestampTools.TimestampFields timestampFields = new TimestampTools.TimestampFields(
                getTimestampFieldSpinnerValue(timestampYearSpinner),
                getTimestampFieldSpinnerValue(timestampMonthSpinner),
                getTimestampFieldSpinnerValue(timestampDaySpinner),
                getTimestampFieldSpinnerValue(timestampHourSpinner),
                getTimestampFieldSpinnerValue(timestampMinuteSpinner),
                getTimestampFieldSpinnerValue(timestampSecondSpinner)
            );
            long computedTimestamp = TimestampTools.toTimestamp(timestampFields, getTimestampSelectedZoneIdAsStr());
            timestampSpinner.setValue(computedTimestamp);
            timestampTextArea.setText(TimestampTools.getTimeStampAsHumanDatetime(computedTimestamp, getTimestampSelectedZoneIdAsStr()));
        } catch (Exception e) {
            timestampTextArea.setText("Error: " + e.getMessage());
        }
    }

    private long getTimestampFieldSpinnerValue(JSpinner jSpinner) {
        Object spinnerValue = jSpinner.getValue();
        if (spinnerValue instanceof Double) {
            return ((Double) jSpinner.getValue()).longValue();
        }
        if (spinnerValue instanceof Integer) {
            return ((Integer) jSpinner.getValue()).longValue();
        }
        return (Long) jSpinner.getValue();
    }

    private String getTimestampSelectedZoneIdAsStr() {
        ComboBoxWithImageItem value = (ComboBoxWithImageItem) timestampTimezoneComboBox.getSelectedItem();
        if (value == null) {
            return ZoneId.systemDefault().toString();
        }
        return value.title();
    }

    private void setupDataFakerTool() {

    }
}
