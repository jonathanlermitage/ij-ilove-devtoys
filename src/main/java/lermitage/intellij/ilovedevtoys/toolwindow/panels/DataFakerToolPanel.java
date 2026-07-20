package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.DataFakerToolSetup;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DataFakerToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JComboBox<String> dataFakerGeneratorComboBox;
    private JButton dataFakerGenerateButton;
    private JComboBox<String> dataFakerLocaleComboBox;
    private JTextArea dataFakerTextArea;

    public DataFakerToolPanel() {
        new DataFakerToolSetup(
            dataFakerGeneratorComboBox,
            dataFakerGenerateButton,
            dataFakerLocaleComboBox,
            dataFakerTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
