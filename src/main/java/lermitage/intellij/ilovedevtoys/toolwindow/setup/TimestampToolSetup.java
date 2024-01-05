package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ComboboxSpeedSearch;
import lermitage.intellij.ilovedevtoys.tools.TimestampTools;
import lermitage.intellij.ilovedevtoys.toolwindow.ComboBoxWithImageItem;
import lermitage.intellij.ilovedevtoys.toolwindow.ComboBoxWithImageRenderer;
import lermitage.intellij.ilovedevtoys.toolwindow.DevToysToolWindow;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TimestampToolSetup extends AbstractToolSetup {

    private final JComboBox<ComboBoxWithImageItem> timestampTimezoneComboBox;
    private final JTextArea timestampTextArea;
    private final JSpinner timestampSpinner;
    private final JButton timestampNowButton;
    private final JButton timestampUpdateFromTimestampButton;
    private final JButton timestampUpdateFromFieldsButton;
    private final JLabel timestampWarningNoZoneIdLabel;
    private final JSpinner timestampYearSpinner;
    private final JSpinner timestampDaySpinner;
    private final JSpinner timestampMonthSpinner;
    private final JSpinner timestampHourSpinner;
    private final JSpinner timestampMinuteSpinner;
    private final JSpinner timestampSecondSpinner;
    private final JSpinner timestampMillisecondSpinner;
    private final JComboBox<String> timestampResolutionComboBox;
    private final JLabel timestampMillisecondLabel;

    public TimestampToolSetup(JComboBox<ComboBoxWithImageItem> timestampTimezoneComboBox,
                              JTextArea timestampTextArea,
                              JSpinner timestampSpinner,
                              JButton timestampNowButton,
                              JButton timestampUpdateFromTimestampButton,
                              JButton timestampUpdateFromFieldsButton,
                              JLabel timestampWarningNoZoneIdLabel,
                              JSpinner timestampYearSpinner,
                              JSpinner timestampDaySpinner,
                              JSpinner timestampMonthSpinner,
                              JSpinner timestampHourSpinner,
                              JSpinner timestampMinuteSpinner,
                              JSpinner timestampSecondSpinner,
                              JSpinner timestampMillisecondSpinner,
                              JComboBox<String> timestampResolutionComboBox,
                              JLabel timestampMillisecondLabel) {
        this.timestampTimezoneComboBox = timestampTimezoneComboBox;
        this.timestampTextArea = timestampTextArea;
        this.timestampSpinner = timestampSpinner;
        this.timestampNowButton = timestampNowButton;
        this.timestampUpdateFromTimestampButton = timestampUpdateFromTimestampButton;
        this.timestampUpdateFromFieldsButton = timestampUpdateFromFieldsButton;
        this.timestampWarningNoZoneIdLabel = timestampWarningNoZoneIdLabel;
        this.timestampYearSpinner = timestampYearSpinner;
        this.timestampDaySpinner = timestampDaySpinner;
        this.timestampMonthSpinner = timestampMonthSpinner;
        this.timestampHourSpinner = timestampHourSpinner;
        this.timestampMinuteSpinner = timestampMinuteSpinner;
        this.timestampSecondSpinner = timestampSecondSpinner;
        this.timestampMillisecondSpinner = timestampMillisecondSpinner;
        this.timestampResolutionComboBox = timestampResolutionComboBox;
        this.timestampMillisecondLabel = timestampMillisecondLabel;
        ComboboxSpeedSearch.installSpeedSearch(timestampTimezoneComboBox, ComboBoxWithImageItem::displayName);
    }

    public void setup() {
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
        for (int i = 0; i < timestampTimezoneComboBox.getItemCount(); i++) {
            ComboBoxWithImageItem comboBoxWithImageItem = timestampTimezoneComboBox.getItemAt(i);
            if (comboBoxWithImageItem.title().equalsIgnoreCase(ZoneId.systemDefault().toString())) {
                timestampTimezoneComboBox.setSelectedIndex(i);
                break;
            }
        }

        timestampResolutionComboBox.addItem("seconds");
        timestampResolutionComboBox.addItem("milliseconds");
        timestampResolutionComboBox.addItemListener(e -> {
            timestampMillisecondSpinner.setValue(0);
            timestampMillisecondSpinner.setVisible(!isEpochSec());
            timestampMillisecondLabel.setVisible(!isEpochSec());
        });
        timestampMillisecondSpinner.setValue(0);
        timestampMillisecondSpinner.setVisible(!isEpochSec());
        timestampMillisecondLabel.setVisible(!isEpochSec());
        timestampResolutionComboBox.setSelectedIndex(0);

        timestampWarningNoZoneIdLabel.setVisible(false);

        long now = TimestampTools.getNowAsTimestampSec();
        timestampSpinner.setModel(new SpinnerNumberModel(now, 0D, 9999999999999D, 1D));
        timestampSpinner.setEditor(new JSpinner.NumberEditor(timestampSpinner, "#"));
        timestampSpinner.setValue(now);

        timestampYearSpinner.setEditor(new JSpinner.NumberEditor(timestampYearSpinner, "#"));
        timestampMonthSpinner.setEditor(new JSpinner.NumberEditor(timestampMonthSpinner, "#"));
        timestampDaySpinner.setEditor(new JSpinner.NumberEditor(timestampDaySpinner, "#"));
        timestampHourSpinner.setEditor(new JSpinner.NumberEditor(timestampHourSpinner, "#"));
        timestampMinuteSpinner.setEditor(new JSpinner.NumberEditor(timestampMinuteSpinner, "#"));
        timestampSecondSpinner.setEditor(new JSpinner.NumberEditor(timestampSecondSpinner, "#"));
        timestampMillisecondSpinner.setEditor(new JSpinner.NumberEditor(timestampMillisecondSpinner, "#"));

        updateTimestampToolOnTimestampSpinnerUpdate();

        timestampNowButton.addActionListener(e -> {
            if (timestampResolutionComboBox.getSelectedIndex() == 0) {
                timestampSpinner.setValue(TimestampTools.getNowAsTimestampSec());
            } else {
                timestampSpinner.setValue(TimestampTools.getNowAsTimestampMillis());
            }
            updateTimestampToolOnTimestampSpinnerUpdate();
        });

        timestampTimezoneComboBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (timestampTimezoneComboBox.getItemCount() > 0) {
                    updateTimestampToolOnTimestampSpinnerUpdate();
                }
            }
        });
        /*timestampSpinner.addChangeListener(e -> updateTimestampToolOnTimestampSpinnerUpdate(false));
        timestampYearSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampMonthSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampDaySpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampHourSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampMinuteSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampSecondSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampMillisecondSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());*/

        timestampNowButton.setIcon(IconLoader.getIcon("ilovedevtoys/toolicons/refresh.svg", DevToysToolWindow.class));
        timestampUpdateFromTimestampButton.setIcon(IconLoader.getIcon("ilovedevtoys/toolicons/refresh.svg", DevToysToolWindow.class));
        timestampUpdateFromFieldsButton.setIcon(IconLoader.getIcon("ilovedevtoys/toolicons/refresh.svg", DevToysToolWindow.class));
        timestampUpdateFromTimestampButton.addActionListener(e -> updateTimestampToolOnTimestampSpinnerUpdate());
        timestampUpdateFromFieldsButton.addActionListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
    }

    private void updateTimestampToolOnTimestampSpinnerUpdate() {
        try {
            long spinnerLongValue = getSpinnerValue(timestampSpinner);
            TimestampTools.TimestampFields timestampFields = TimestampTools.toTimestampFields(spinnerLongValue, isEpochSec());
            timestampYearSpinner.setValue(timestampFields.year());
            timestampMonthSpinner.setValue(timestampFields.month());
            timestampDaySpinner.setValue(timestampFields.day());
            timestampHourSpinner.setValue(timestampFields.hours());
            timestampMinuteSpinner.setValue(timestampFields.minutes());
            timestampSecondSpinner.setValue(timestampFields.seconds());
            timestampMillisecondSpinner.setValue(timestampFields.millis());
            timestampTextArea.setText(TimestampTools.getTimeStampAsHumanDatetime(spinnerLongValue, getTimestampSelectedZoneIdAsStr(), isEpochSec()));
        } catch (Exception e) {
            timestampTextArea.setText("Error: " + e.getMessage());
        }
    }

    private void updateTimestampToolOnTimestampFieldsUpdate() {
        try {
            TimestampTools.TimestampFields timestampFields = new TimestampTools.TimestampFields(
                getSpinnerValue(timestampYearSpinner),
                getSpinnerValue(timestampMonthSpinner),
                getSpinnerValue(timestampDaySpinner),
                getSpinnerValue(timestampHourSpinner),
                getSpinnerValue(timestampMinuteSpinner),
                getSpinnerValue(timestampSecondSpinner),
                getSpinnerValue(timestampMillisecondSpinner)
            );
            long computedTimestamp = TimestampTools.toTimestamp(timestampFields, getTimestampSelectedZoneIdAsStr(), isEpochSec());
            timestampSpinner.setValue(computedTimestamp);
            timestampTextArea.setText(TimestampTools.getTimeStampAsHumanDatetime(computedTimestamp, getTimestampSelectedZoneIdAsStr(), isEpochSec()));
        } catch (Exception e) {
            timestampTextArea.setText("Error: " + e.getMessage());
        }
    }

    private boolean isEpochSec() {
        return timestampResolutionComboBox.getSelectedIndex() == 0;
    }

    private String getTimestampSelectedZoneIdAsStr() {
        ComboBoxWithImageItem value = (ComboBoxWithImageItem) timestampTimezoneComboBox.getSelectedItem();
        if (value == null) {
            return ZoneId.systemDefault().toString();
        }
        return value.title();
    }
}
