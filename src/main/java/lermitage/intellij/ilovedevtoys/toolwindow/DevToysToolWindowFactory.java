package lermitage.intellij.ilovedevtoys.toolwindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class DevToysToolWindowFactory implements ToolWindowFactory, DumbAware {

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        DevToysToolWindow myToolWindow = new DevToysToolWindow();
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(myToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
