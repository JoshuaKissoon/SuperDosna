package dosna.gui;

import dosna.SystemObjectsManager;
import dosna.connections.ConnectionsManager;
import dosna.osn.actor.Actor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import kademlia.dht.GetParameter;
import socialkademlia.dht.JSocialKademliaStorageEntry;
import kademlia.exceptions.ContentNotFoundException;

/**
 * The main component of the add connection form is this panel.
 * We make this panel separate however, so that other parts of the system can use it.
 *
 * @author Joshua Kissoon
 * @since 20140404
 *
 * @todo when a actor is already your connection, don't show the addfriend button
 */
public class ConnectionAddPanel extends JPanel
{

    /* Properties */
    private final static int FRAME_WIDTH = 450;
    private final static int FRAME_HEIGHT = 250;

    /* Main components */
    private final SystemObjectsManager systemObjects;

    /* Form components */
    private final JTextField searchBox;
    private final JPanel resultsPanel;

    /**
     * Setup the add connection panel
     *
     * @param systemObjects Used to put and get data
     */
    public ConnectionAddPanel(final SystemObjectsManager systemObjects)
    {
        /* Setup the Panel */
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        this.systemObjects = systemObjects;

        Border b = new EmptyBorder(10, 10, 10, 10);

        /* Setup the search box */
        searchBox = new JTextField();
        searchBox.setBorder(b);
        searchBox.addActionListener(new SearchActionListener());
        this.add(searchBox, BorderLayout.NORTH);

        /* Search Results panel */
        resultsPanel = new JPanel();
        resultsPanel.setBorder(b);
        this.add(resultsPanel, BorderLayout.CENTER);

    }

    /**
     * Update the JPanel and it's data.
     */
    public void refresh()
    {
        this.invalidate();
        this.validate();
        this.repaint();
    }

    /**
     * Updates the result panel with the given result.
     *
     * @param conn The actor that was found
     */
    private void setResult(final Actor conn)
    {
        /* Empty the results */
        resultsPanel.removeAll();
        resultsPanel.setLayout(new BorderLayout());

        /* Place the new actor there */
        final JPanel actorData = new JPanel();
        actorData.setLayout(new BoxLayout(actorData, BoxLayout.Y_AXIS));

        actorData.add(new JLabel("User ID: " + conn.getId()));
        actorData.add(new JLabel("Name: " + conn.getName()));

        resultsPanel.add(actorData, BorderLayout.CENTER);

        /* Add the Add button */
        final JButton btn = new JButton("Add Connection");
        btn.setBorder(new EmptyBorder(10, 10, 10, 10));
        btn.putClientProperty("actor", conn);
        btn.addActionListener(new ConnectionAddActionListener());
        resultsPanel.add(btn, BorderLayout.EAST);

        /* Refresh to show data to actor */
        this.refresh();
    }

    private class SearchActionListener implements ActionListener
    {

        /**
         * Handle searching when the user presses enter in the searchbox
         */
        @Override
        public void actionPerformed(final ActionEvent evt)
        {
            final String txt = searchBox.getText();

            /* Only do something if they user has entered some text */
            if (!txt.trim().isEmpty())
            {
                Actor u = new Actor(txt);

                /* Create our GetParameter to do the search */
                GetParameter gp = new GetParameter(u.getKey(), u.getType());

                /* Now lets search for content */
                JSocialKademliaStorageEntry val = null;
                try
                {
                    val = ConnectionAddPanel.this.systemObjects.getDataManager().get(gp);
                    u = (Actor) new Actor().fromSerializedForm(val.getContent());
                    ConnectionAddPanel.this.setResult(u);
                }
                catch (ContentNotFoundException | IOException ex)
                {
                    System.err.println("Ran into a prob when searching for person. Message: " + ex.getMessage());
                }

                if (val != null)
                {

                }
            }
        }
    }

    /**
     * The default class to handle the event where a user wants to connect to another user.
     *
     * @author Joshua Kissoon
     * @since 20140404
     */
    private class ConnectionAddActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(final ActionEvent evt)
        {
            final JButton btn = (JButton) evt.getSource();
            final Actor connection = (Actor) btn.getClientProperty("actor");

            if (new ConnectionsManager(systemObjects).addConnection(systemObjects.getActor(), connection))
            {
                JOptionPane.showMessageDialog(null, "You have successfully added this connection!! Congrats!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "An error occured whiles adding this connection! Please try again later.");
            }
        }
    }
}
