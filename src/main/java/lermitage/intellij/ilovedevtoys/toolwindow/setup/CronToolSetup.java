package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.cronutils.model.CronType;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.diagnostic.Logger;
import lermitage.intellij.ilovedevtoys.tools.CronTools;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CronToolSetup extends AbstractToolSetup {

    private final JTextField cronExpressionTextField;
    private final JSpinner cronExpressionHowManyDaysSpinner;
    private final JComboBox<String> cronTypeComboBox;
    private final JTextArea cronTextArea;
    private final JButton explainButton;

    private static final Logger LOGGER = Logger.getInstance(CronToolSetup.class);
    private static final int TIMEOUT_SEC = 20;

    public CronToolSetup(JTextField cronExpressionTextField,
                         JSpinner cronExpressionHowManyDaysSpinner,
                         JComboBox<String> cronTypeComboBox,
                         JTextArea cronTextArea,
                         JButton explainButton) {
        this.cronExpressionTextField = cronExpressionTextField;
        this.cronExpressionHowManyDaysSpinner = cronExpressionHowManyDaysSpinner;
        this.cronTypeComboBox = cronTypeComboBox;
        this.cronTextArea = cronTextArea;
        this.explainButton = explainButton;
    }

    public void setup() {
        cronExpressionTextField.setText("* * * * *");
        cronExpressionHowManyDaysSpinner.setValue(2L);
        explainButton.setText("Explain");
        cronTypeComboBox.addItem(CronType.UNIX.name());
        cronTypeComboBox.addItem(CronType.QUARTZ.name());
        cronTypeComboBox.addItem(CronType.SPRING53.name());
        cronTypeComboBox.addItem(CronType.CRON4J.name());
        explainButton.addActionListener(actionListener -> {

                long nbDays = this.getSpinnerValue(cronExpressionHowManyDaysSpinner);
                if (cronExpressionTextField.getText().startsWith("* * ")) {
                    if (nbDays > 100) {
                        cronTextArea.setText("Error: computing execution time for the next " + nbDays + " days " +
                            "may consume a large amount of memory and freeze or kill the IDE. Please lower this value " +
                            "(100 days max for '* * ...' cron expressions).");
                        return;
                    }
                } else {
                    if (nbDays > 400) {
                        cronTextArea.setText("Error: computing execution time for the next " + nbDays + " days " +
                            "may consume a large amount of memory and freeze or kill the IDE. Please lower this value " +
                            "(400 days max).");
                        return;
                    }
                }

                explainButton.setText("Explain (computing...)");
                explainButton.setEnabled(false);
                cronTextArea.setText("");
                Application application = ApplicationManager.getApplication();
                Runnable runnable = () -> {

                    try {
                        Thread actionThread = new Thread(() -> {
                            cronTextArea.setText(
                                CronTools.explainAndGetDates(
                                    cronExpressionTextField.getText(),
                                    CronType.valueOf((String) cronTypeComboBox.getSelectedItem()),
                                    nbDays));
                            cronTextArea.setCaretPosition(0);
                            explainButton.setText("Explain");
                            explainButton.setEnabled(true);
                        });

                        Thread progressThread = new Thread(() -> {
                            int nbSec = 0;
                            while (actionThread.isAlive()) {
                                try {
                                    //noinspection BusyWait
                                    Thread.sleep(1000);
                                    nbSec++;
                                    explainButton.setText("Explain (computing, " + nbSec + " sec elapsed...)");
                                    if (nbSec >= TIMEOUT_SEC) {
                                        cronTextArea.setText("Error: spent too much time computing cron expression's next " +
                                            "executions (" + TIMEOUT_SEC + " seconds max). Please lower the number of days " +
                                            "or update the cron expression.");
                                        actionThread.interrupt();
                                        break;
                                    }
                                } catch (InterruptedException e) {
                                    LOGGER.error(e);
                                    explainButton.setText("Explain");
                                    explainButton.setEnabled(true);
                                    actionThread.interrupt();
                                    break;
                                }
                            }
                            explainButton.setText("Explain");
                            explainButton.setEnabled(true);
                        });

                        actionThread.start();
                        progressThread.start();

                    } catch (Exception e) {
                        LOGGER.error(e);
                    }

                };
                application.invokeAndWait(() -> application.runReadAction(runnable), ModalityState.nonModal());
            }
        );
    }
}
