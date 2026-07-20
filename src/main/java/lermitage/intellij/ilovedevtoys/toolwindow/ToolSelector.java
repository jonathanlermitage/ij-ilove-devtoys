package lermitage.intellij.ilovedevtoys.toolwindow;

import lermitage.intellij.ilovedevtoys.toolwindow.panels.ToolPanel;

/**
 * Lets a tool switch to (and interact with) another tool. Selecting a tool lazily creates its
 * panel if needed, makes it visible, and returns it so the caller can push data into it.
 */
public interface ToolSelector {

    /**
     * Selects the tool identified by {@code toolName}, lazily creating its panel if it has not been
     * displayed yet, makes it the visible tool, and returns it.
     */
    ToolPanel selectTool(String toolName);
}
