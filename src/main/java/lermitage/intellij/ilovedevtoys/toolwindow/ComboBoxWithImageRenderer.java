package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.openapi.util.IconLoader;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

public class ComboBoxWithImageRenderer extends JLabel implements ListCellRenderer<Object> {

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
        ComboBoxWithImageItem item = (ComboBoxWithImageItem) value;
        setText(item.title());
        setIcon(IconLoader.getIcon(item.imagePath(), ComboBoxWithImageRenderer.class));
        setIconTextGap(6);
        return this;
    }
}
