package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.SetDiffTools;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

public class SetDiffToolSetup extends AbstractToolSetup {

    private final JButton setDiffCompareButton;
    private final JCheckBox setDiffCaseSensitiveCheckBox;
    private final JTextArea setDiffTextArea1;
    private final JTextArea setDiffTextArea2;
    private final JTextArea setDiffResultTextArea;
    private final JCheckBox setDiffIgnoreEmptyLinesCheckBox;

    public SetDiffToolSetup(JButton setDiffCompareButton,
                            JCheckBox setDiffCaseSensitiveCheckBox,
                            JTextArea setDiffTextArea1,
                            JTextArea setDiffTextArea2,
                            JTextArea setDiffResultTextArea,
                            JCheckBox setDiffIgnoreEmptyLinesCheckBox) {
        this.setDiffCompareButton = setDiffCompareButton;
        this.setDiffCaseSensitiveCheckBox = setDiffCaseSensitiveCheckBox;
        this.setDiffTextArea1 = setDiffTextArea1;
        this.setDiffTextArea2 = setDiffTextArea2;
        this.setDiffResultTextArea = setDiffResultTextArea;
        this.setDiffIgnoreEmptyLinesCheckBox = setDiffIgnoreEmptyLinesCheckBox;
    }

    public void setup() {
        setDiffCaseSensitiveCheckBox.setSelected(true);
        setDiffIgnoreEmptyLinesCheckBox.setSelected(true);
        setDiffCompareButton.addActionListener(e -> {
            setDiffResultTextArea.setText(
                SetDiffTools.compareSets(
                    setDiffTextArea1.getText(),
                    setDiffTextArea2.getText(),
                    setDiffCaseSensitiveCheckBox.isSelected(),
                    setDiffIgnoreEmptyLinesCheckBox.isSelected()
                )
            );
            setDiffResultTextArea.setCaretPosition(0);
            updateWithBestNumberOfRows(setDiffTextArea1, setDiffTextArea2, setDiffResultTextArea);
        });
    }
}
