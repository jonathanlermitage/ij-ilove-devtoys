package lermitage.intellij.ilovedevtoys.toolwindow;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

public class ComboBoxWithImageRenderer<ImageIcon> extends JLabel implements ListCellRenderer<ImageIcon> {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    ComboBoxWithImageRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        javax.swing.ImageIcon icon = (javax.swing.ImageIcon) value;
        setText(icon.getDescription());
        setIcon(icon);
        setIconTextGap(6);
        return this;
    }
}
