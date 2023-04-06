package hubertmap.view;

import javax.swing.event.*;

/**
 * The SimpleDocumentListener interface extends the DocumentListener interface and provides a simple
 * way to listen for changes to a document. This interface defines a single method, update, which is
 * called whenever the document is updated.
 */
public interface SimpleDocumentListener extends DocumentListener {

    /**
     * This method is called whenever the document is updated.
     *
     * @param e a DocumentEvent object representing the event that occurred
     */
    void update(DocumentEvent e);

    /** {@inheritDoc} */
    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }

    /** {@inheritDoc} */
    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }

    /** {@inheritDoc} */
    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}
