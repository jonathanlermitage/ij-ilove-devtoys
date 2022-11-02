package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.intellij.openapi.util.IconLoader;
import lermitage.intellij.ilovedevtoys.tools.TimestampTools;
import lermitage.intellij.ilovedevtoys.toolwindow.ComboBoxWithImageItem;
import lermitage.intellij.ilovedevtoys.toolwindow.ComboBoxWithImageRenderer;
import lermitage.intellij.ilovedevtoys.toolwindow.DevToysToolWindow;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TimestampToolSetup {

    private final JComboBox<ComboBoxWithImageItem> timestampTimezoneComboBox;
    private final JTextArea timestampTextArea;
    private final JSpinner timestampSpinner;
    private final JButton timestampNowButton;
    private final JButton timestampUpdateFromTimestampButton;
    private final JTextField timestampFilterTextField;
    private final JButton timestampUpdateFromFieldsButton;
    private final JLabel timestampWarningNoZoneIdLabel;
    private final JSpinner timestampYearSpinner;
    private final JSpinner timestampDaySpinner;
    private final JSpinner timestampMonthSpinner;
    private final JSpinner timestampHourSpinner;
    private final JSpinner timestampMinuteSpinner;
    private final JSpinner timestampSecondSpinner;

    // Used to avoid infinitive loops on timestamp spinners update (main spinner updates detailed
    // spinners (year, month... second spinners), and detailed spinners update main spinner).
    private boolean timestampUpdateTriggeredByCode = false;

    public TimestampToolSetup(JComboBox<ComboBoxWithImageItem> timestampTimezoneComboBox,
                              JTextArea timestampTextArea,
                              JSpinner timestampSpinner,
                              JButton timestampNowButton,
                              JButton timestampUpdateFromTimestampButton,
                              JTextField timestampFilterTextField,
                              JButton timestampUpdateFromFieldsButton,
                              JLabel timestampWarningNoZoneIdLabel,
                              JSpinner timestampYearSpinner,
                              JSpinner timestampDaySpinner,
                              JSpinner timestampMonthSpinner,
                              JSpinner timestampHourSpinner,
                              JSpinner timestampMinuteSpinner,
                              JSpinner timestampSecondSpinner) {
        this.timestampTimezoneComboBox = timestampTimezoneComboBox;
        this.timestampTextArea = timestampTextArea;
        this.timestampSpinner = timestampSpinner;
        this.timestampNowButton = timestampNowButton;
        this.timestampUpdateFromTimestampButton = timestampUpdateFromTimestampButton;
        this.timestampFilterTextField = timestampFilterTextField;
        this.timestampUpdateFromFieldsButton = timestampUpdateFromFieldsButton;
        this.timestampWarningNoZoneIdLabel = timestampWarningNoZoneIdLabel;
        this.timestampYearSpinner = timestampYearSpinner;
        this.timestampDaySpinner = timestampDaySpinner;
        this.timestampMonthSpinner = timestampMonthSpinner;
        this.timestampHourSpinner = timestampHourSpinner;
        this.timestampMinuteSpinner = timestampMinuteSpinner;
        this.timestampSecondSpinner = timestampSecondSpinner;
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

        timestampWarningNoZoneIdLabel.setVisible(false);

        long now = TimestampTools.getNowAsTimestamp();
        timestampSpinner.setModel(new SpinnerNumberModel(now, 0L, 9999999999L, 1D));
        timestampSpinner.setEditor(new JSpinner.NumberEditor(timestampSpinner, "#"));
        timestampSpinner.setValue(now);

        timestampYearSpinner.setEditor(new JSpinner.NumberEditor(timestampYearSpinner, "#"));
        timestampMonthSpinner.setEditor(new JSpinner.NumberEditor(timestampMonthSpinner, "#"));
        timestampDaySpinner.setEditor(new JSpinner.NumberEditor(timestampDaySpinner, "#"));
        timestampHourSpinner.setEditor(new JSpinner.NumberEditor(timestampHourSpinner, "#"));
        timestampMinuteSpinner.setEditor(new JSpinner.NumberEditor(timestampMinuteSpinner, "#"));
        timestampSecondSpinner.setEditor(new JSpinner.NumberEditor(timestampSecondSpinner, "#"));

        updateTimestampToolOnTimestampSpinnerUpdate(true);

        timestampNowButton.addActionListener(e -> {
            timestampSpinner.setValue(TimestampTools.getNowAsTimestamp());
            updateTimestampToolOnTimestampSpinnerUpdate(true);
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
                    updateTimestampToolOnTimestampSpinnerUpdate(false);
                }
            }
        });
        timestampSpinner.addChangeListener(e -> updateTimestampToolOnTimestampSpinnerUpdate(false));
        timestampYearSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampMonthSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampDaySpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampHourSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampMinuteSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
        timestampSecondSpinner.addChangeListener(e -> updateTimestampToolOnTimestampFieldsUpdate());

        timestampFilterTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                timestampTimezoneComboBox.setSelectedIndex(-1);
                timestampTimezoneComboBox.removeAllItems();
                //timestampTimezoneComboBox.removeAll();
                Map<String, String> zoneIdsAndFlags = TimestampTools.getAllAvailableZoneIdesAndFlags();
                List<String> zoneIds = zoneIdsAndFlags.keySet().stream()
                    .filter(zoneId -> zoneId.toUpperCase().contains(timestampFilterTextField.getText().toUpperCase()))
                    .sorted(Comparator.comparing(String::toUpperCase)).toList();
                if (zoneIds.isEmpty()) {
                    timestampTimezoneComboBox.setVisible(false);
                    timestampTimezoneComboBox.setSelectedIndex(-1);
                    timestampWarningNoZoneIdLabel.setVisible(true);
                } else {
                    zoneIds.forEach(zoneId -> {
                        String flag = zoneIdsAndFlags.get(zoneId);
                        if (flag == null) {
                            flag = "_null";
                        }
                        timestampTimezoneComboBox.addItem(new ComboBoxWithImageItem(
                            zoneId, "ilovedevtoys/flags/" + flag + ".svg"));
                    });
                    timestampTimezoneComboBox.setVisible(true);
                    timestampTimezoneComboBox.setSelectedIndex(0);
                    timestampWarningNoZoneIdLabel.setVisible(false);

                    if (timestampFilterTextField.getText().isBlank()) {
                        for (int i = 0; i < timestampTimezoneComboBox.getItemCount(); i++) {
                            ComboBoxWithImageItem comboBoxWithImageItem = timestampTimezoneComboBox.getItemAt(i);
                            if (comboBoxWithImageItem.title().equalsIgnoreCase(ZoneId.systemDefault().toString())) {
                                timestampTimezoneComboBox.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                }
            }
        });

        timestampUpdateFromTimestampButton.setIcon(IconLoader.getIcon("ilovedevtoys/toolicons/refresh.svg", DevToysToolWindow.class));
        timestampUpdateFromFieldsButton.setIcon(IconLoader.getIcon("ilovedevtoys/toolicons/refresh.svg", DevToysToolWindow.class));
        timestampUpdateFromTimestampButton.addActionListener(e -> updateTimestampToolOnTimestampSpinnerUpdate(true));
        timestampUpdateFromFieldsButton.addActionListener(e -> updateTimestampToolOnTimestampFieldsUpdate());
    }

    private void updateTimestampToolOnTimestampSpinnerUpdate(boolean forceUpdate) {
        if (!forceUpdate && timestampUpdateTriggeredByCode) {
            timestampUpdateTriggeredByCode = false;
            return;
        }
        try {
            timestampUpdateTriggeredByCode = true;
            long spinnerLongValue = getTimestampFieldSpinnerValue(timestampSpinner);
            TimestampTools.TimestampFields timestampFields = TimestampTools.toTimestampFields(spinnerLongValue);
            timestampYearSpinner.setValue(timestampFields.year());
            timestampMonthSpinner.setValue(timestampFields.month());
            timestampDaySpinner.setValue(timestampFields.day());
            timestampHourSpinner.setValue(timestampFields.hours());
            timestampMinuteSpinner.setValue(timestampFields.minutes());
            timestampSecondSpinner.setValue(timestampFields.seconds());
            timestampTextArea.setText(TimestampTools.getTimeStampAsHumanDatetime(spinnerLongValue, getTimestampSelectedZoneIdAsStr()));
        } catch (Exception e) {
            timestampTextArea.setText("Error: " + e.getMessage());
        }
    }

    private void updateTimestampToolOnTimestampFieldsUpdate() {
        if (timestampUpdateTriggeredByCode) {
            timestampUpdateTriggeredByCode = false;
            return;
        }
        try {
            timestampUpdateTriggeredByCode = true;
            TimestampTools.TimestampFields timestampFields = new TimestampTools.TimestampFields(
                getTimestampFieldSpinnerValue(timestampYearSpinner),
                getTimestampFieldSpinnerValue(timestampMonthSpinner),
                getTimestampFieldSpinnerValue(timestampDaySpinner),
                getTimestampFieldSpinnerValue(timestampHourSpinner),
                getTimestampFieldSpinnerValue(timestampMinuteSpinner),
                getTimestampFieldSpinnerValue(timestampSecondSpinner)
            );
            long computedTimestamp = TimestampTools.toTimestamp(timestampFields, getTimestampSelectedZoneIdAsStr());
            timestampSpinner.setValue(computedTimestamp);
            timestampTextArea.setText(TimestampTools.getTimeStampAsHumanDatetime(computedTimestamp, getTimestampSelectedZoneIdAsStr()));
        } catch (Exception e) {
            timestampTextArea.setText("Error: " + e.getMessage());
        }
    }

    private long getTimestampFieldSpinnerValue(JSpinner jSpinner) {
        Object spinnerValue = jSpinner.getValue();
        if (spinnerValue instanceof Double) {
            return ((Double) jSpinner.getValue()).longValue();
        }
        if (spinnerValue instanceof Integer) {
            return ((Integer) jSpinner.getValue()).longValue();
        }
        return (Long) jSpinner.getValue();
    }

    private String getTimestampSelectedZoneIdAsStr() {
        ComboBoxWithImageItem value = (ComboBoxWithImageItem) timestampTimezoneComboBox.getSelectedItem();
        if (value == null) {
            return ZoneId.systemDefault().toString();
        }
        return value.title();
    }
}
