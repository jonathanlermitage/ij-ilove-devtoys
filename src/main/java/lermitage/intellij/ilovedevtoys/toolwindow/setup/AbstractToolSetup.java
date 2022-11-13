package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import javax.swing.JSpinner;
import javax.swing.JTextArea;
import java.util.Collections;
import java.util.stream.Stream;

public abstract class AbstractToolSetup {

    /**
     * Set the same number of rows for every JTextArea in order to avoid unwanted resizing of JTextAreas on content update.
     * <b>Please invoke this method after each JTextArea content update.</b>
     * Without this method, the JTextArea with the highest number of rows would become bigger than the other JTextAreas.
     */
    public void updateWithBestNumberOfRows(JTextArea... jTextAreas) {
        int maxNumberOfRows = Collections.max(Stream.of(jTextAreas).map(JTextArea::getLineCount).toList());
        for (JTextArea jTextArea : jTextAreas) {
            jTextArea.setRows(maxNumberOfRows);
        }
    }

    public long getSpinnerValue(JSpinner jSpinner) {
        Object spinnerValue = jSpinner.getValue();
        if (spinnerValue instanceof Double) {
            return ((Double) jSpinner.getValue()).longValue();
        }
        if (spinnerValue instanceof Integer) {
            return ((Integer) jSpinner.getValue()).longValue();
        }
        return (Long) jSpinner.getValue();
    }
}
