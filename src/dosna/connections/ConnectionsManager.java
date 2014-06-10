package dosna.connections;

import dosna.dhtAbstraction.DataManager;
import dosna.gui.ConnectionAddPanel;
import dosna.messaging.MessageBox;
import dosna.osn.actor.Actor;
import dosna.osn.actor.Relationship;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that takes care of various connections management operations
 *
 * @author Joshua Kissoon
 * @since 20140610
 */
public class ConnectionsManager
{

    private final DataManager dataManager;

    /**
     * Construct a new ConnectionsManager
     *
     * @param dataManager
     */
    public ConnectionsManager(final DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Creates a new relationship between two actors
     *
     * @param actor      The owner of this relationship
     * @param connection The actor to be connected to the relationship owner
     *
     * @return Whether the operations were successful or not
     */
    public boolean addConnection(final Actor actor, final Actor connection)
    {
        /* Create a new relationship object and add it to the actor's profile */
        Relationship r = new Relationship(actor, connection);
        actor.getConnectionManager().addConnection(r);
        actor.setUpdated();

        try
        {
            /* Now let's update this actor object  on the DHT */
            dataManager.putAndCache(actor);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ConnectionAddPanel.class.getName()).log(Level.SEVERE, "Unable to upload User object after add connection.", ex);
            return false;
        }

        /* Create a new messagebox between these actors */
        MessageBox mb = new MessageBox(actor.getId(), connection.getId());

        try
        {
            this.dataManager.putAndCache(mb);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ConnectionAddPanel.class.getName()).log(Level.SEVERE, "Unable to upload newly created message box.", ex);
            return false;
        }

        /* Everything's good if we got here! lets return true. */
        return true;
    }
}
