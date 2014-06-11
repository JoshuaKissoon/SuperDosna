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
     * Load the MessageBox between the currently logged in actor and the specified connection from the DHT
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
}
