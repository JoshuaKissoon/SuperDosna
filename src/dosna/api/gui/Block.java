package dosna.api.gui;

import javax.swing.JPanel;

/**
 * Interface used to define blocks used in the UI
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface Block
{

    /**
     * Method used to return the block to be put in the UI
     *
     * @return JPanel
     */
    public JPanel getBlock();

    /**
     * @return The ID for the section in which this block should be placed
     */
    public String getSectionId();
}
