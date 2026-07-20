package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.ComboBoxWithImageItem;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.TimestampToolSetup;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

public class TimestampToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JComboBox<ComboBoxWithImageItem> timestampTimezoneComboBox;
    private JTextArea timestampTextArea;
    private JSpinner timestampSpinner;
    private JButton timestampNowButton;
    private JButton timestampUpdateFromTimestampButton;
    private JButton timestampUpdateFromFieldsButton;
    private JLabel timestampWarningNoZoneIdLabel;
    private JSpinner timestampYearSpinner;
    private JSpinner timestampDaySpinner;
    private JSpinner timestampMonthSpinner;
    private JSpinner timestampHourSpinner;
    private JSpinner timestampMinuteSpinner;
    private JSpinner timestampSecondSpinner;
    private JSpinner timestampMillisecondSpinner;
    private JComboBox<String> timestampResolutionComboBox;
    private JLabel timestampMillisecondLabel;

    public TimestampToolPanel() {
        new TimestampToolSetup(
            timestampTimezoneComboBox,
            timestampTextArea,
            timestampSpinner,
            timestampNowButton,
            timestampUpdateFromTimestampButton,
            timestampUpdateFromFieldsButton,
            timestampWarningNoZoneIdLabel,
            timestampYearSpinner,
            timestampDaySpinner,
            timestampMonthSpinner,
            timestampHourSpinner,
            timestampMinuteSpinner,
            timestampSecondSpinner,
            timestampMillisecondSpinner,
            timestampResolutionComboBox,
            timestampMillisecondLabel).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>" +
            "Type a timestamp or update datetime field(s)<br>" +
            "then hit the <i>Update from timestamp</i> or<br>" +
            "<i>Update from fields</i> button.</html>";
    }
}
