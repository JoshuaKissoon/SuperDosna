package dosna.osn.actor;

import dosna.dhtAbstraction.DataManager;
import dosna.notification.NotificationBox;
import java.io.IOException;
import kademlia.dht.GetParameter;
import kademlia.dht.StorageEntry;
import kademlia.exceptions.ContentNotFoundException;
import kademlia.node.Node;

/**
 * A class that handles actors on the DOSN.
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public class ActorManager
{

    private final DataManager dataManager;

    /**
     * Initialize the actor creator class
     *
     * @param dataManager The Network Data Manager
     */
    public ActorManager(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Method that creates the new actor
     *
     * @param actor The actor to place on the DHT
     *
     * @return Boolean whether the actor creation was successful or not
     *
     * @throws java.io.IOException
     */
    public Actor createActor(Actor actor) throws IOException
    {
        /* Lets create a new notification box for this actor */
        NotificationBox nb = new NotificationBox(actor);
        dataManager.putAndCache(nb);
        actor.setNotificationBoxNid(nb.getKey());

        dataManager.putAndCache(actor);

        return actor;
    }

    /**
     * Load the actor object from the DHT
     *
     * @param id The actor's user ID on the network
     *
     * @return The actor
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public Actor loadActor(String id) throws IOException, ContentNotFoundException
    {
        Actor a = new Actor(id);
        GetParameter gp = new GetParameter(a.getKey(), a.getType(), a.getId());
        StorageEntry se = dataManager.getAndCache(gp);
        return (Actor) new Actor().fromBytes(se.getContent().getBytes());
    }

    /**
     * Put the updated actor object on the DHT.
     *
     * We cache the actor object locally also since we only update actor objects of connections and the local user.
     *
     * @param actor The actor object to update
     */
    public void updateActorOnDHT(final Actor actor)
    {
        try
        {
            this.dataManager.putAndCache(actor);
        }
        catch (IOException ex)
        {
            System.err.println("ActorManager.updateActorOnDHT() failed to update actor. Msg: " + ex.getMessage());
        }
    }

    /**
     * To use some of the features of SocialKad, an actor's node must be placed on the actor's profile for others to find it.
     *
     * This method is called to update the actor node if it has changed.
     *
     * We first check if the node is changed, and do an update only if it has changed.
     *
     * @param actor   The actor to update
     * @param newNode The new actor's node
     */
    public void updateActorNode(final Actor actor, final Node newNode)
    {
        Node old = actor.getActorNode();

        /* Check if there is a difference in the old and new node, and update the actor object with the new node if there is */
        boolean isDifference = false;
        if (!old.equals(newNode))
        {
            isDifference = true;
        }
        else if (old.getSocketAddress().equals(newNode.getSocketAddress()))
        {
            isDifference = true;
        }

        if (isDifference)
        {
            actor.setActorNode(newNode);
            this.updateActorOnDHT(actor);
        }
    }
}
