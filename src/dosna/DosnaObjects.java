package dosna;

import dosna.dhtAbstraction.DataManager;
import dosna.messaging.MessagingManager;
import dosna.osn.actor.Actor;

/**
 * Class that contain all the DOSNA Objects that allow functionality throughout the system
 * - This class will also provide singleton methods to access common objects
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class DosnaObjects
{

    private final DataManager dataManager;
    private final Actor actor;

    /* Setup a messaging manager */
    private MessagingManager messagingManager = null;

    /**
     * Create a new instance of the class
     *
     * @param dataManager The DataManager used throughout the system
     * @param actor       The logged in actor
     */
    public DosnaObjects(final DataManager dataManager, final Actor actor)
    {
        this.dataManager = dataManager;
        this.actor = actor;
    }

    public DataManager getDataManager()
    {
        return this.dataManager;
    }

    public Actor getActor()
    {
        return this.actor;
    }

    /**
     * If a MessagingManager is initialized, we send it here, else we initialize one and return it
     *
     * @return An initialized MessagingManager
     */
    public MessagingManager getMessagingManager()
    {
        if (this.messagingManager == null)
        {
            this.messagingManager = new MessagingManager(this);
        }

        return this.messagingManager;
    }
}
