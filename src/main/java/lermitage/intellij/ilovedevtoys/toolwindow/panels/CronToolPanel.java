package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.CronToolSetup;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CronToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JTextField cronExpressionTextField;
    private JSpinner cronExpressionHowManyDaysSpinner;
    private JComboBox<String> cronTypeComboBox;
    private JTextArea cronTextArea;
    private JButton explainButton;

    public CronToolPanel() {
        new CronToolSetup(
            cronExpressionTextField,
            cronExpressionHowManyDaysSpinner,
            cronTypeComboBox,
            cronTextArea,
            explainButton).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }
}
