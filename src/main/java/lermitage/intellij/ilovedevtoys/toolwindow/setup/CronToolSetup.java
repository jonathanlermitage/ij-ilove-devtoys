package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.cronutils.model.CronType;
import lermitage.intellij.ilovedevtoys.tools.CronTools;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CronToolSetup {

    private final JTextField cronExpressionTextField;
    private final JComboBox<String> cronTypeComboBox;
    private final JTextArea cronTextArea;
    private final JButton explainButton;

    public CronToolSetup(JTextField cronExpressionTextField,
                         JComboBox<String> cronTypeComboBox,
                         JTextArea cronTextArea,
                         JButton explainButton) {
        this.cronExpressionTextField = cronExpressionTextField;
        this.cronTypeComboBox = cronTypeComboBox;
        this.cronTextArea = cronTextArea;
        this.explainButton = explainButton;
    }

    public void setup() {
        cronTypeComboBox.addItem(CronType.UNIX.name());
        cronTypeComboBox.addItem(CronType.QUARTZ.name());
        cronTypeComboBox.addItem(CronType.SPRING53.name());
        cronTypeComboBox.addItem(CronType.CRON4J.name());
        explainButton.addActionListener(e -> {
                cronTextArea.setText(
                    CronTools.explainAndGetDates(
                        cronExpressionTextField.getText(),
                        CronType.valueOf((String) cronTypeComboBox.getSelectedItem()),
                        4));
                cronTextArea.setCaretPosition(0);
            }
        );
    }
}
