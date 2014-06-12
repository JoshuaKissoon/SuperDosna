package dosna.messaging;

import dosna.DosnaObjects;
import java.io.IOException;
import kademlia.dht.GetParameter;
import kademlia.exceptions.ContentNotFoundException;

/**
 * A Manager class that manages various messaging tasks
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class MessagingManager
{

    private final DosnaObjects dosnaObjects;

    /**
     * Create a new Messaging Manager object
     *
     * @param dosnaObjects The objects are required to perform the various operations
     */
    public MessagingManager(final DosnaObjects dosnaObjects)
    {
        this.dosnaObjects = dosnaObjects;
    }

    /**
     * Load the MessageBox owned by the current actor
     * - Containing messages between the currently logged in actor and the specified connection from the DHT
     *
     * @param connectionAid The ActorId of the connection with whom the MessageBox is shared
     *
     * @return The loaded messagebox
     *
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public MessageBox loadMessageBox(final String connectionAid) throws ContentNotFoundException
    {
        /* Create a temporary messagebox to get the DHT key of the required MessageBox */
        MessageBox temp = new MessageBox(this.dosnaObjects.getActor().getId(), connectionAid);

        try
        {
            GetParameter gp = new GetParameter(temp.getKey(), temp.getType(), temp.getOwnerId());
            MessageBox box = (MessageBox) temp.fromSerializedForm(this.dosnaObjects.getDataManager().get(gp).getContent());
            return box;
        }
        catch (IOException | ContentNotFoundException ex)
        {
            throw new ContentNotFoundException(ex.getMessage());
        }
    }

    /**
     * Load the MessageBox owned by the specified connection
     * - Containing messages between the currently logged in actor and the specified connection
     *
     * @param connectionAid The ActorId of the connection
     *
     * @return The connection's MessageBox
     *
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public MessageBox loadConnectionMessageBox(final String connectionAid) throws ContentNotFoundException
    {
        /* Create a temporary messagebox to get the DHT key of the required MessageBox */
        MessageBox temp = new MessageBox(connectionAid, this.dosnaObjects.getActor().getId());

        try
        {
            GetParameter gp = new GetParameter(temp.getKey(), temp.getType(), temp.getOwnerId());
            MessageBox box = (MessageBox) temp.fromSerializedForm(this.dosnaObjects.getDataManager().get(gp).getContent());
            return box;
        }
        catch (IOException | ContentNotFoundException ex)
        {
            throw new ContentNotFoundException(ex.getMessage());
        }
    }

    /**
     * Send a message from one user to another
     *
     * Since each Actor keeps a MessageBox between itself and it's connection, we'll have to update 2 MessageBoxes
     *
     * @param connnectionAid The ActorId of the connection to send this message to
     * @param msg            The message to send
     *
     * @return The updated MessageBox of the current user
     *
     * @throws kademlia.exceptions.ContentNotFoundException
     * @throws java.io.IOException
     */
    public MessageBox sendMessage(final Message msg, final String connnectionAid) throws ContentNotFoundException, IOException
    {
        /**
         * Put the message in the actor->connection MessageBox
         * - update this MessageBox on the DHT
         */
        MessageBox actorMb = this.loadMessageBox(connnectionAid);
        actorMb.addMessage(msg);
        this.dosnaObjects.getDataManager().put(actorMb);

        /**
         * Put the message in the connection->actor MessageBox
         * - update this MessageBox on the DHT
         */
        MessageBox connectionMb = this.loadConnectionMessageBox(connnectionAid);
        connectionMb.addMessage(msg);
        this.dosnaObjects.getDataManager().put(connectionMb);

        /* Return the updated messagebox of this actor */
        return actorMb;
    }
}
