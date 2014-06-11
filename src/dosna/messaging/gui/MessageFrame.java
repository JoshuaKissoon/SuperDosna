package dosna.messaging.gui;

import dosna.DosnaObjects;
import dosna.gui.util.GBConstraints;
import dosna.messaging.Message;
import dosna.messaging.MessageBox;
import dosna.messaging.MessagingManager;
import dosna.osn.actor.Relationship;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.Collection;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import kademlia.exceptions.ContentNotFoundException;

/**
 * This frame shows all messages provides the messaging facility
 *
 * @author Joshua
 * @since
 */
public final class MessageFrame extends JFrame implements Runnable
{

    /* Frame properties */
    private final int width = 1000, height = 500;

    /* Panel to show a list of contacts */
    private JList contactsList;

    /* Panel to show the data for a single contact */
    private JPanel contactPanel;

    private final DosnaObjects dosnaObjects;

    /**
     * Setup a new messenger frame
     *
     * @param dosnaObjects The set of DOSNA objects
     */
    public MessageFrame(final DosnaObjects dosnaObjects)
    {
        this.dosnaObjects = dosnaObjects;
    }

    /**
     * Create the GUI and it's contents
     */
    public final void create()
    {
        this.getContentPane().setLayout(new BorderLayout());

        this.getContentPane().add(this.getContactsList(), BorderLayout.WEST);

        this.getContentPane().add(this.getContactPanel(), BorderLayout.CENTER);
    }

    /**
     * Update the messages panel showing messages from a specific connection
     *
     * @param connectionAid The ActorId of the connection
     */
    public final void updateContactPanel(final String connectionAid)
    {
        this.getContentPane().remove(this.contactPanel);
        this.getContentPane().add(this.getContactPanel(connectionAid), BorderLayout.CENTER);
        this.refresh();
    }

    /**
     * Here we setup and load the contacts
     */
    private JList getContactsList()
    {
        /* Setup the contacts list model */
        DefaultListModel listModel = new DefaultListModel();

        Collection<Relationship> connections = this.dosnaObjects.getActor().getConnectionManager().getRelationships();

        for (Relationship r : connections)
        {
            listModel.addElement(r.getConnectionUid());
        }

        /* Setup the list and scroll pane */
        contactsList = new JList(listModel);
        contactsList.addListSelectionListener(new ContactsListListener());

        JScrollPane scp = new JScrollPane(contactsList);
        scp.setSize(new Dimension(300, this.height));
        scp.setMinimumSize(new Dimension(300, this.height));

        return contactsList;
    }

    /**
     * Here we setup and load the contact panel
     *
     * We load a blank panel in this scenario
     */
    private JPanel getContactPanel()
    {
        /* Setup the panel */
        contactPanel = new JPanel();

        return contactPanel;
    }

    /**
     * Get the contact panel with
     * - Messages from a specific connection
     * - A message sending form
     */
    private JPanel getContactPanel(final String connectionAid)
    {
        /* Setup the panel */
        contactPanel = new JPanel(new BorderLayout());

        /* Setup a panel for messages and load the messages */
        final JPanel messagesPanel = new JPanel(new GridBagLayout());

        try
        {
            MessageBox mb = new MessagingManager(this.dosnaObjects).loadMessageBox(connectionAid);

            int counter = 0;
            for (Message m : mb.getMessages())
            {
                JLabel msgLbl = new JLabel(m.getText());
                messagesPanel.add(msgLbl, GBConstraints.getItemConstraints(0, counter++));
            }

            System.out.println(mb);
        }
        catch (ContentNotFoundException ex)
        {

        }

        /* Add the messages panel to the contact panel */
        contactPanel.add(messagesPanel, BorderLayout.CENTER);

        /* Add the message add form */
        MessageSendingForm msf = new MessageSendingForm(this.dosnaObjects, connectionAid);
        contactPanel.add(msf, BorderLayout.PAGE_END);

        return contactPanel;
    }

    /**
     * Displays the GUI onto the screen
     */
    public final void display()
    {
        this.setTitle("Messenger");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(this.width, this.height));
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Refresh the content on the GUI
     */
    public final void refresh()
    {
        this.invalidate();
        this.validate();
        this.repaint();
    }

    /**
     * When the run method is called, we'll launch the frame
     */
    @Override
    public void run()
    {
        this.create();
        this.display();
    }

    private final class ContactsListListener implements ListSelectionListener
    {

        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            if (e.getValueIsAdjusting())
            {
                System.out.println(contactsList.getSelectedValue());
                MessageFrame.this.updateContactPanel((String) contactsList.getSelectedValue());
            }
        }

    }

}
