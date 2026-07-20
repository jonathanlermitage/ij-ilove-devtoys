package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ComboboxSpeedSearch;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.ASCIIHEXToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.BENCODEJSONToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.Base64ToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.CronToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.DataFakerToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.EscapeToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.HMACToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.HashToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.JSONStringToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.JSONYAMLToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.LinesUtilsToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.LoremIpsumToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.PasswordStrengthToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.PasswordVerifierToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.PropertiesYamlToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.TimestampToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.ToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.UUIDToolPanel;
import lermitage.intellij.ilovedevtoys.toolwindow.panels.URLCodecToolPanel;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DevToysToolWindow implements ToolSelector {

    private JPanel mainPanel;
    private JPanel toolContainerPanel;
    private JComboBox<ComboBoxWithImageItem> toolComboBox;
    private JLabel helpLabel;

    private final CardLayout cardLayout = new CardLayout();

    /** Tool panels already created (and added as cards), keyed by tool title. Filled lazily on first selection. */
    private final Map<String, ToolPanel> builtPanels = new HashMap<>();

    /** Tool title -> icon + lazy panel factory, in combo box display order. */
    private final LinkedHashMap<String, ToolDescriptor> registry = new LinkedHashMap<>();

    private record ToolDescriptor(String icon, Supplier<ToolPanel> factory) {
    }

    public DevToysToolWindow() {
        String iconsPath = "ilovedevtoys/toolicons/";
        registry.put("Base64 encoder/decoder", new ToolDescriptor(iconsPath + "Base64EncoderDecoder.svg", Base64ToolPanel::new));
        registry.put("URL encoder/decoder", new ToolDescriptor(iconsPath + "UrlEncoderDecoder.svg", URLCodecToolPanel::new));
        registry.put("Fake Data generator", new ToolDescriptor(iconsPath + "DataFaker.svg", DataFakerToolPanel::new));
        registry.put("Timestamp converter", new ToolDescriptor(iconsPath + "Timestamp.svg", TimestampToolPanel::new));
        registry.put("Cron parser", new ToolDescriptor(iconsPath + "CronParser.svg", CronToolPanel::new));
        registry.put("Lines utils", new ToolDescriptor(iconsPath + "SetDiff.svg", () -> new LinesUtilsToolPanel(helpLabel)));
        registry.put("Lorem Ipsum generator", new ToolDescriptor(iconsPath + "LoremIpsumGenerator.svg", LoremIpsumToolPanel::new));
        registry.put("Hash generator", new ToolDescriptor(iconsPath + "HashGenerator.svg", HashToolPanel::new));
        registry.put("HMAC generator", new ToolDescriptor(iconsPath + "HMACGenerator.svg", HMACToolPanel::new));
        registry.put("UUID generator", new ToolDescriptor(iconsPath + "UuidGenerator.svg", UUIDToolPanel::new));
        registry.put("Password strength evaluator", new ToolDescriptor(iconsPath + "PasswordStrengthEvaluator.svg", () -> new PasswordStrengthToolPanel(this)));
        registry.put("Password hash verifier", new ToolDescriptor(iconsPath + "PasswordHashVerifier.svg", PasswordVerifierToolPanel::new));
        registry.put("Text escape/unescape", new ToolDescriptor(iconsPath + "Escaper.svg", EscapeToolPanel::new));
        registry.put("ASCII <> HEX converter", new ToolDescriptor(iconsPath + "AsciiHex.svg", ASCIIHEXToolPanel::new));
        registry.put("BENCODE <> JSON converter", new ToolDescriptor(iconsPath + "BencodeJson.svg", BENCODEJSONToolPanel::new));
        registry.put("JSON <> YAML converter", new ToolDescriptor(iconsPath + "JsonYaml.svg", JSONYAMLToolPanel::new));
        registry.put("JSON to String converter", new ToolDescriptor(iconsPath + "JsonString.svg", JSONStringToolPanel::new));
        registry.put("Properties to YAML converter ", new ToolDescriptor(iconsPath + "PropertiesYaml.svg", PropertiesYamlToolPanel::new));

        toolContainerPanel.setLayout(cardLayout);

        registry.forEach((title, descriptor) -> toolComboBox.addItem(new ComboBoxWithImageItem(title, descriptor.icon())));
        toolComboBox.setRenderer(new ComboBoxWithImageRenderer());
        toolComboBox.setMaximumRowCount(11);
        ComboboxSpeedSearch.installSpeedSearch(toolComboBox, ComboBoxWithImageItem::displayName);

        helpLabel.setText("");
        helpLabel.setIcon(IconLoader.getIcon(iconsPath + "contextHelp.svg", DevToysToolWindow.class));
        helpLabel.setToolTipText("");
        helpLabel.setVisible(false);

        toolComboBox.addActionListener(e -> {
            int selectedIndex = toolComboBox.getSelectedIndex();
            if (selectedIndex >= 0) {
                selectTool(toolComboBox.getItemAt(selectedIndex).title());
            }
        });
        toolComboBox.setSelectedIndex(0);
    }

    @Override
    public ToolPanel selectTool(String toolName) {
        ToolPanel panel = builtPanels.computeIfAbsent(toolName, name -> {
            ToolPanel created = registry.get(name).factory().get();
            toolContainerPanel.add(created.getRootPanel(), name); // register the card once
            return created;
        });

        // keep the combo box in sync when a tool switches to another tool (e.g. password strength -> hash generator).
        // This may re-enter selectTool via the combo listener, but with the combo already in sync, so it stops there.
        ComboBoxWithImageItem selected = (ComboBoxWithImageItem) toolComboBox.getSelectedItem();
        if (selected == null || !selected.title().equals(toolName)) {
            selectComboByName(toolName);
        }

        cardLayout.show(toolContainerPanel, toolName);

        String tooltip = panel.helpTooltip();
        helpLabel.setVisible(tooltip != null);
        if (tooltip != null) {
            helpLabel.setToolTipText(tooltip);
        }
        panel.onActivate();
        return panel;
    }

    private void selectComboByName(String toolName) {
        for (int i = 0; i < toolComboBox.getItemCount(); i++) {
            if (toolComboBox.getItemAt(i).title().equals(toolName)) {
                toolComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public JPanel getContent() {
        return mainPanel;
    }
}
