package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.PropertiesYamlToolSetup;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PropertiesYamlToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JComboBox<String> propertiesYamlTypeComboBox;
    private JTextArea propertiesYamlPropertiesTextArea;
    private JTextArea propertiesYamlYamlTextArea;

    public PropertiesYamlToolPanel() {
        new PropertiesYamlToolSetup(
            propertiesYamlTypeComboBox,
            propertiesYamlPropertiesTextArea,
            propertiesYamlYamlTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
