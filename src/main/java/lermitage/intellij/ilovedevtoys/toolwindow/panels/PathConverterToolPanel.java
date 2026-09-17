package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExtendableTextField;
import lermitage.intellij.ilovedevtoys.tools.PathTools;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.PathConverterToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.List;

import org.intellij.lang.annotations.Language;



public class PathConverterToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private ExtendableTextField pathInputTextField;
    private JBTextField pathDriveLetterTextField;
    private ExtendableTextField pathWindowsTextField;
    private ExtendableTextField pathWindowsAltTextField;
    private ExtendableTextField pathGitBashTextField;
    private ExtendableTextField pathWslTextField;
    private ExtendableTextField pathUnixTextField;

    /**
     * @param project current project, whose location seeds the drive letter offered by default. It may be
     *                the default project, which has no location.
     */
    public PathConverterToolPanel(Project project) {
        new PathConverterToolSetup(
            pathInputTextField,
            pathDriveLetterTextField,
            List.of(
                new PathConverterToolSetup.PathRow(pathWindowsTextField, PathTools.ConvertedPaths::windows),
                new PathConverterToolSetup.PathRow(pathWindowsAltTextField, PathTools.ConvertedPaths::windowsAlt),
                new PathConverterToolSetup.PathRow(pathGitBashTextField, PathTools.ConvertedPaths::gitBash),
                new PathConverterToolSetup.PathRow(pathWslTextField, PathTools.ConvertedPaths::wsl),
                new PathConverterToolSetup.PathRow(pathUnixTextField, PathTools.ConvertedPaths::unix)),
            PathTools.defaultDriveLetter(project == null ? null : project.getBasePath())).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        @SuppressWarnings("HtmlRequiredLangAttribute")
        @Language("HTML")
        String tip = "<html>" +
            "Paste a path in any format, and it will be converted " +
            "to every other format as you type.<br><br>" +
            "The Drive field supplies the drive letter when the pasted " +
            "path has none and follows the path when it has one.<br><br>" +
            "A home directory is recognized in every format, so " +
            "<code>~/foo</code> and <code>/Users/you/foo</code> become <code>C:\\Users\\you\\foo</code>.<br><br>" +
            "Nota: <code>/c/foo</code> is read as a Git Bash path rather than " +
            "as a Unix directory named <code>/c</code>." +
            "</html>";
        return tip;
    }
}
