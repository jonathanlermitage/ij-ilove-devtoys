package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import lermitage.intellij.ilovedevtoys.tools.LinesUtilsTools;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class LinesUtilsToolSetup extends AbstractToolSetup {

    private final JLabel helpLabel;
    private final JComboBox<String> linesUtilsComboBox;
    private final JButton linesUtilsCompareButton;
    private final JCheckBox linesUtilsCaseSensitiveCheckBox;
    private final JTextArea linesUtilsTextArea1;
    private final JTextArea linesUtilsTextArea2;
    private final JTextArea linesUtilsResultTextArea;
    private final JCheckBox linesUtilsIgnoreEmptyLinesCheckBox;

    public LinesUtilsToolSetup(JLabel helpLabel,
                               JComboBox<String> linesUtilsComboBox,
                               JButton linesUtilsCompareButton,
                               JCheckBox linesUtilsCaseSensitiveCheckBox,
                               JTextArea linesUtilsTextArea1,
                               JTextArea linesUtilsTextArea2,
                               JTextArea linesUtilsResultTextArea,
                               JCheckBox linesUtilsIgnoreEmptyLinesCheckBox) {
        this.helpLabel = helpLabel;
        this.linesUtilsComboBox = linesUtilsComboBox;
        this.linesUtilsCompareButton = linesUtilsCompareButton;
        this.linesUtilsCaseSensitiveCheckBox = linesUtilsCaseSensitiveCheckBox;
        this.linesUtilsTextArea1 = linesUtilsTextArea1;
        this.linesUtilsTextArea2 = linesUtilsTextArea2;
        this.linesUtilsResultTextArea = linesUtilsResultTextArea;
        this.linesUtilsIgnoreEmptyLinesCheckBox = linesUtilsIgnoreEmptyLinesCheckBox;
    }

    public void activate() {
        helpLabel.setVisible(true);
        linesUtilsComboBox.setSelectedIndex(0);
    }

    public void setup() {
        linesUtilsComboBox.addItem("Set Diff viewer");
        linesUtilsComboBox.addItem("Lines merging");
        linesUtilsComboBox.addItem("Lines subtract");
        linesUtilsComboBox.addActionListener(e -> {
                linesUtilsIgnoreEmptyLinesCheckBox.setVisible(false);
                switch (linesUtilsComboBox.getSelectedIndex()) {
                    case 0 -> {
                        helpLabel.setToolTipText("<html>" +
                            "Type some text in Set 1 and Set 2 then hit <i>Compare</i><br>" +
                            "in order to see if some lines exist only in Set 1 or<br>" +
                            "in Set 2.</html>");
                        linesUtilsCompareButton.setText("Compare");
                        linesUtilsIgnoreEmptyLinesCheckBox.setVisible(true);
                    }
                    case 1 -> {
                        helpLabel.setToolTipText("<html>Type some text in Text 1 and Text 2 then hit <i>Merge</i><br>" +
                            "in order to add the lines of Set 2 to Set 1 if they're new.</html>");
                        linesUtilsCompareButton.setText("Merge");
                    }
                    case 2 -> {
                        helpLabel.setToolTipText("<html>Type some text in Text 1 and Text 2 then hit <i>Subtract</i><br>" +
                            "in order to remove the lines of Set 2 from Set 1.</html>");
                        linesUtilsCompareButton.setText("Subtract");
                    }
                }
            }
        );
        linesUtilsCaseSensitiveCheckBox.setSelected(true);
        linesUtilsIgnoreEmptyLinesCheckBox.setSelected(true);
        linesUtilsCompareButton.addActionListener(e -> {
            switch (linesUtilsComboBox.getSelectedIndex()) {
                case 0 -> linesUtilsResultTextArea.setText(
                    LinesUtilsTools.compareSets(
                        linesUtilsTextArea1.getText(),
                        linesUtilsTextArea2.getText(),
                        linesUtilsCaseSensitiveCheckBox.isSelected(),
                        linesUtilsIgnoreEmptyLinesCheckBox.isSelected()
                    )
                );
                case 1 -> linesUtilsResultTextArea.setText(
                    LinesUtilsTools.mergeSets(
                        linesUtilsTextArea1.getText(),
                        linesUtilsTextArea2.getText(),
                        linesUtilsCaseSensitiveCheckBox.isSelected()
                    )
                );
                case 2 -> linesUtilsResultTextArea.setText(
                    LinesUtilsTools.subtractSets(
                        linesUtilsTextArea1.getText(),
                        linesUtilsTextArea2.getText(),
                        linesUtilsCaseSensitiveCheckBox.isSelected()
                    )
                );
            }
            linesUtilsResultTextArea.setCaretPosition(0);
            updateWithBestNumberOfRows(linesUtilsTextArea1, linesUtilsTextArea2, linesUtilsResultTextArea);
        });
    }
}
