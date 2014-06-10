package dosna.api.gui;

import javax.swing.JMenuItem;

/**
 * Specify the structure of DOSNA Menu Items.
 * DOSNA menu items will be based on Java's JMenuItem.
 *
 * @author Joshua Kissoon
 * @since 20140610
 */
public interface DosnaMenuItem
{

    /**
     * @return The JMenuItem for this menu
     */
    public JMenuItem getMenuItem();
}
