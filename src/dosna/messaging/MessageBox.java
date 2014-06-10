package dosna.messaging;

import dosna.content.DOSNAContent;
import dosna.util.HashCalculator;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.TreeSet;
import kademlia.node.KademliaId;

/**
 * Class that manages the messages between an actor and another actor;
 *
 * @author Joshua Kissoon
 * @since 20140610
 */
public class MessageBox extends DOSNAContent
{

    public final static String TYPE = "MessageBox";

    private KademliaId key;
    private final String ownerId;
    private final String contactId;

    /* We store messages in a treeset */
    private final TreeSet<Message> messages;

    /**
     * Create a new message box
     *
     * @param ownerId   The ActorId of the owner of this MessageBox
     * @param contactId The ActorId of the actor the owner is contacting
     *
     * @note The actor ID's needn't be in any sequence
     */
    public MessageBox(final String ownerId, final String contactId)
    {
        this.ownerId = ownerId;
        this.contactId = contactId;

        this.messages = new TreeSet<>();

        this.generateKey();
    }

    /**
     * Generate the MessageBox KademliaId based on the Actors' IDs
     */
    private void generateKey()
    {
        /* Concatenate the actor IDs */
        String raw = ownerId + contactId;

        byte[] keyData = new byte[20];

        try
        {
            keyData = HashCalculator.sha1Hash(raw);
        }
        catch (NoSuchAlgorithmException ex)
        {

        }

        this.key = new KademliaId(keyData);
    }

    /**
     * Add a new message to this MessageBox
     *
     * @param m The new message to add
     */
    public void addMessage(Message m)
    {
        this.messages.add(m);
    }

    /**
     * @return A Collection of all the messages in this MessageBox
     */
    public Collection<Message> getMessages()
    {
        return this.messages;
    }

    @Override
    public KademliaId getKey()
    {
        return this.key;
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    @Override
    public String getOwnerId()
    {
        return this.ownerId;
    }

}
