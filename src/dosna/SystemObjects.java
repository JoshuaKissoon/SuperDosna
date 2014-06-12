package dosna;

import dosna.api.notification.NotificationsManager;
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
public class SystemObjects
{

    private final DataManager dataManager;
    private final Actor actor;

    /* Setup a messaging manager */
    private MessagingManager messagingManager = null;

    /* Instance of Notifications Manager */
    private NotificationsManager notificationsManager = null;

    /**
     * Create a new instance of the class
     *
     * @param dataManager The DataManager used throughout the system
     * @param actor       The logged in actor
     */
    public SystemObjects(final DataManager dataManager, final Actor actor)
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

    /**
     * If a NotificationManager is already initialized, we return that one, else we initialize a new NotificationManager instance
     *
     * @return An instance of NotificationManager
     */
    public NotificationsManager getNotificationsManager()
    {
        if (this.notificationsManager == null)
        {
            this.notificationsManager = new NotificationsManager(this.getDataManager());
        }

        return this.notificationsManager;
    }
}
