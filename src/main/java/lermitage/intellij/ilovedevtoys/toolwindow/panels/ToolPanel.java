package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import javax.swing.JComponent;

/**
 * A single DevToys tool's UI. Each implementation is bound to its own {@code .form} file and is
 * instantiated lazily (only when its tool is selected for the first time), so its widgets and
 * listeners are built on demand instead of all at once when the tool window opens.
 */
public interface ToolPanel {

    /** The tool's root component, added as a card to the tool window's container. */
    JComponent getRootPanel();

    /**
     * Tooltip to display on the tool window's shared help icon while this tool is visible,
     * or {@code null} to hide the help icon for this tool.
     */
    default String helpTooltip() {
        return null;
    }

    /** Called each time this tool becomes visible. */
    default void onActivate() {
    }
}
