package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import com.intellij.ui.components.JBTextField;
import lermitage.intellij.ilovedevtoys.toolwindow.setup.URLCodecToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class URLCodecToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JBTextField urlCodecDecodedTextField;
    private JBTextField urlCodecEncodedTextField;

    public URLCodecToolPanel() {
        new URLCodecToolSetup(
            urlCodecDecodedTextField,
            urlCodecEncodedTextField).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>" +
            "Type decoded or encoded URL and it will be<br>" +
            "automatically converted as you type.</html>";
    }
}
