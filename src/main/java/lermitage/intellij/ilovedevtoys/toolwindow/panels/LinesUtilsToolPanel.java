package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.LinesUtilsToolSetup;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LinesUtilsToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JComboBox<String> linesUtilsComboBox;
    private JButton linesUtilsCompareButton;
    private JCheckBox linesUtilsCaseSensitiveCheckBox;
    private JTextArea linesUtilsTextArea1;
    private JTextArea linesUtilsTextArea2;
    private JTextArea linesUtilsResultTextArea;
    private JCheckBox linesUtilsIgnoreEmptyLinesCheckBox;

    private final LinesUtilsToolSetup linesUtilsToolSetup;

    public LinesUtilsToolPanel(JLabel helpLabel) {
        linesUtilsToolSetup = new LinesUtilsToolSetup(
            helpLabel,
            linesUtilsComboBox,
            linesUtilsCompareButton,
            linesUtilsCaseSensitiveCheckBox,
            linesUtilsTextArea1,
            linesUtilsTextArea2,
            linesUtilsResultTextArea,
            linesUtilsIgnoreEmptyLinesCheckBox);
        linesUtilsToolSetup.setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public void onActivate() {
        linesUtilsToolSetup.activate();
    }
}
