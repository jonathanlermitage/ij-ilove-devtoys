package lermitage.intellij.ilovedevtoys.toolwindow.setup;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.fields.ExtendableTextComponent;
import com.intellij.ui.components.fields.ExtendableTextField;
import lermitage.intellij.ilovedevtoys.tools.PathTools;

import javax.swing.Icon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;



/**
 * Wires the Path converter tool: re-renders every output format as the input path or the drive letter
 * changes, and copies a rendered path to the clipboard, optionally wrapped in double quotes.
 */
public class PathConverterToolSetup extends AbstractToolSetup {

    private static final String COPY_QUOTED_ICON = "ilovedevtoys/toolicons/copyQuoted.svg";
    private static final Pattern A_TO_Z_PATTERN = Pattern.compile("[^A-Za-z]");

    private final ExtendableTextField pathInputTextField;
    private final JBTextField pathDriveLetterTextField;
    private final List<PathRow> pathRows;
    private final String defaultDriveLetter;

    /**
     * Guards against the input and drive letter listeners re-entering each other: syncing the drive letter
     * field from the input path mutates a second document while the first one is still notifying.
     */
    private boolean updating;

    /** Clear icon drawn inside the right end of the input field, shown only while the field holds text. */
    private ExtendableTextComponent.Extension clearExtension;

    /**
     * One output format.
     *
     * @param field read-only field displaying the rendered path, with its copy icons drawn inside it
     * @param value reads this format out of a conversion result
     */
    public record PathRow(
        ExtendableTextField field,
        Function<PathTools.ConvertedPaths, String> value) {
    }

    /**
     * @param defaultDriveLetter drive letter to start with, used until the user edits it or until an input
     *                           path carries one of its own
     */
    public PathConverterToolSetup(ExtendableTextField pathInputTextField,
                                  JBTextField pathDriveLetterTextField,
                                  List<PathRow> pathRows,
                                  String defaultDriveLetter) {
        this.pathInputTextField = pathInputTextField;
        this.pathDriveLetterTextField = pathDriveLetterTextField;
        this.pathRows = pathRows;
        this.defaultDriveLetter = defaultDriveLetter;
    }

    public void setup() {
        pathInputTextField.setToolTipText("Paste a path in any format: Windows, Git Bash, WSL or Unix/macOS.");
        clearExtension = ExtendableTextComponent.Extension.create(
            AllIcons.Actions.Close, AllIcons.Actions.CloseHovered, "Clear", () -> pathInputTextField.setText(""));
        pathDriveLetterTextField.setToolTipText("<html>Drive letter used when the pasted path has none,<br>" +
            "for example when converting a Unix path to Windows.</html>");
        restrictToSingleDriveLetter();
        pathDriveLetterTextField.setText(defaultDriveLetter);

        Icon copyQuotedIcon = IconLoader.getIcon(COPY_QUOTED_ICON, PathConverterToolSetup.class);
        for (PathRow pathRow : pathRows) {
            // Trailing extensions are laid out right to left, so the first one listed takes the rightmost
            // slot. Listing the quoted copy first therefore reads as Copy, then Copy Quoted on screen.
            pathRow.field().setExtensions(
                copyExtension(pathRow.field(), copyQuotedIcon,
                    "Copy surrounded by double quotes", PathTools::quote),
                copyExtension(pathRow.field(), AllIcons.Actions.Copy,
                    "Copy", path -> path));
        }

        // a DocumentListener also catches right-click Paste and drag and drop, which a KeyListener would miss.
        // Paths are pasted far more often than typed, so that difference matters here.
        pathInputTextField.getDocument().addDocumentListener(onChange(this::inputChanged));
        pathDriveLetterTextField.getDocument().addDocumentListener(onChange(this::driveLetterChanged));

        update();
        updateClearIcon();
    }

    /** Re-renders every output format from the current input path and drive letter. */
    public void update() {
        PathTools.ConvertedPaths converted = PathTools.convert(pathInputTextField.getText(), currentDriveLetter());
        for (PathRow pathRow : pathRows) {
            pathRow.field().setText(pathRow.value().apply(converted));
            pathRow.field().setCaretPosition(0);
        }
    }

    /**
     * Reflects the drive letter carried by the pasted path back into the drive letter field, so the field
     * always shows the letter actually in use, then re-renders.
     */
    private void inputChanged() {
        if (updating) {
            return;
        }
        updating = true;
        try {
            String inputDriveLetter = PathTools.parse(pathInputTextField.getText()).driveLetter();
            if (inputDriveLetter != null && !inputDriveLetter.equals(currentDriveLetter())) {
                pathDriveLetterTextField.setText(inputDriveLetter);
            }
            update();
            updateClearIcon();
        } finally {
            updating = false;
        }
    }

    private void driveLetterChanged() {
        if (updating) {
            return;
        }
        updating = true;
        try {
            update();
        } finally {
            updating = false;
        }
    }

    /**
     * Shows the inline clear icon only while the input holds text, the way the IDE search fields behave.
     * Adding and removing the extension shifts the field's insets, so it is only touched when it changes.
     */
    private void updateClearIcon() {
        boolean hasText = !pathInputTextField.getText().isEmpty();
        if (hasText == pathInputTextField.getExtensions().contains(clearExtension)) {
            return;
        }
        if (hasText) {
            pathInputTextField.addExtension(clearExtension);
        } else {
            pathInputTextField.removeExtension(clearExtension);
        }
    }

    /** The drive letter currently shown, or the default when the field has been emptied. */
    private String currentDriveLetter() {
        String driveLetter = pathDriveLetterTextField.getText().trim();
        return driveLetter.isEmpty() ? defaultDriveLetter : driveLetter.toUpperCase(Locale.ROOT);
    }

    /**
     * Builds an icon drawn inside the right end of {@code field} that copies its path to the clipboard.
     *
     * @param decorator applied to the path before it is copied, for instance, to surround it with quotes
     */
    private static ExtendableTextComponent.Extension copyExtension(ExtendableTextField field,
                                                                   Icon icon,
                                                                   String tooltip,
                                                                   UnaryOperator<String> decorator) {
        return ExtendableTextComponent.Extension.create(icon, icon, tooltip, () -> {
            String text = decorator.apply(field.getText());
            if (!text.isEmpty()) {
                CopyPasteManager.getInstance().setContents(new StringSelection(text));
            }
        });
    }

    /**
     * Keeps the drive letter field down to a single upper case letter, so it cannot hold a value the
     * conversion would have to guess about. Deletion is left alone, which empties the field back to the default.
     */
    private void restrictToSingleDriveLetter() {
        if (!(pathDriveLetterTextField.getDocument() instanceof AbstractDocument document)) {
            return;
        }
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                replace(fb, offset, 0, text, attrs);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String letters = text == null ? "" : A_TO_Z_PATTERN.matcher(text).replaceAll("");
                if (letters.isEmpty()) {
                    return;
                }
                String lastLetter = letters.substring(letters.length() - 1).toUpperCase(Locale.ROOT);
                fb.replace(0, fb.getDocument().getLength(), lastLetter, attrs);
            }
        });
    }

    /** Adapts a plain action to a {@link DocumentListener}, which has no single method to implement. */
    private static DocumentListener onChange(Runnable action) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                action.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                action.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                action.run();
            }
        };
    }
}
