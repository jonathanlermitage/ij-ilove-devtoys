package lermitage.intellij.ilovedevtoys.toolwindow.panels;

import lermitage.intellij.ilovedevtoys.toolwindow.setup.BENCODEJSONToolSetup;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BENCODEJSONToolPanel implements ToolPanel {

    private JPanel rootPanel;
    private JTextArea bencodejsonBENCODETextArea;
    private JTextArea bencodejsonJSONTextArea;

    public BENCODEJSONToolPanel() {
        new BENCODEJSONToolSetup(
            bencodejsonBENCODETextArea,
            bencodejsonJSONTextArea).setup();
    }

    @Override
    public JComponent getRootPanel() {
        return rootPanel;
    }

    @Override
    public String helpTooltip() {
        return "<html>" +
            "Type some BENCODE or JSON and it will be<br>" +
            "automatically converted as you type.</html>";
    }
}
