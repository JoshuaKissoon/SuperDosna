package dosna.messaging.gui;

import dosna.SystemObjects;
import dosna.api.gui.DosnaMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * A menu item that pops up the messaging window
 *
 * @author Joshua Kissoon
 * @since 20140610
 */
public class MessagesMenuItem implements DosnaMenuItem
{

    private final JMenuItem menuItem;
    private final static String COMMAND = "mmiClicked";

    /* We store the dosna objects to pass to the messenger frame when it's launched */
    private final SystemObjects dosnaObjects;

    public MessagesMenuItem(final SystemObjects dosnaObjects)
    {
        this.dosnaObjects = dosnaObjects;
        menuItem = new JMenuItem("Messaging");
        menuItem.setActionCommand(COMMAND);
        menuItem.addActionListener(new MMIActionListener());
    }

    @Override
    public JMenuItem getMenuItem()
    {
        return this.menuItem;
    }

    private final class MMIActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals(COMMAND))
            {
                /* The messaging menu item was clicked, handle popping up the Messages interface here!! */
                new Thread(new MessageFrame(MessagesMenuItem.this.dosnaObjects)).start();
            }
        }
    }

}
