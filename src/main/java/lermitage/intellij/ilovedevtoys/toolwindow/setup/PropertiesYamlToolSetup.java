package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.PropertiesYAMLTools;
import lermitage.intellij.ilovedevtoys.tools.PropertiesYAMLTools.PropertiesType;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PropertiesYamlToolSetup {

    private final JComboBox<String> propertiesYamlTypeComboBox;
    private final JTextArea propertiesYamlPropertiesTextArea;
    private final JTextArea propertiesYamlYamlTextArea;

    public PropertiesYamlToolSetup(JComboBox<String> propertiesYamlTypeComboBox,
                                   JTextArea propertiesYamlPropertiesTextArea,
                                   JTextArea propertiesYamlYamlTextArea) {
        this.propertiesYamlTypeComboBox = propertiesYamlTypeComboBox;
        this.propertiesYamlPropertiesTextArea = propertiesYamlPropertiesTextArea;
        this.propertiesYamlYamlTextArea = propertiesYamlYamlTextArea;
    }

    public void setup() {
        for (PropertiesType propertiesType : PropertiesType.values()) {
            propertiesYamlTypeComboBox.addItem(propertiesType.name());
        }
        propertiesYamlTypeComboBox.addItemListener(e -> updateTextAreas());
        propertiesYamlPropertiesTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateTextAreas();
            }
        });
    }

    private void updateTextAreas() {
        propertiesYamlYamlTextArea.setText(PropertiesYAMLTools.propertiesToYaml(
            propertiesYamlPropertiesTextArea.getText(),
            PropertiesType.valueOf((String) propertiesYamlTypeComboBox.getSelectedItem())));
    }
}
