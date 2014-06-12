package dosna;

import dosna.notification.PeriodicNotificationsChecker;
import dosna.api.notification.NotificationsManager;
import dosna.dhtAbstraction.DataManager;
import dosna.messaging.MessagingManager;
import dosna.osn.actor.Actor;
import java.io.IOException;

/**
 * Class that contain all the DOSNA Objects that allow functionality throughout the system
 * - This class will also provide singleton methods to access common objects
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class SystemObjectsManager
{

    private final DataManager dataManager;
    private final Actor actor;

    /* Setup a messaging manager */
    private MessagingManager messagingManager = null;

    /* Instance of Notifications Manager */
    private NotificationsManager notificationsManager = null;

    private PeriodicNotificationsChecker notificationsChecker = null;

    /**
     * Create a new instance of the class
     *
     * @param dataManager The DataManager used throughout the system
     * @param actor       The logged in actor
     */
    public SystemObjectsManager(final DataManager dataManager, final Actor actor)
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

    /**
     * Launch the notification checker
     */
    public void launchNotificationsChecker()
    {
        notificationsChecker = new PeriodicNotificationsChecker(this.dataManager, this.actor);
        notificationsChecker.startTimer();
    }

    public PeriodicNotificationsChecker getNotificationsChecker()
    {
        if(this.notificationsChecker == null)
        {
            System.err.println("Notifications checker not initialized");
        }
        return this.notificationsChecker;
    }

    /**
     * Shuts down the core components of dosna
     *
     * @param saveState
     *
     * @throws java.io.IOException
     */
    public void shutDown(final boolean saveState) throws IOException
    {
        this.dataManager.shutdown(saveState);
        this.notificationsChecker.shutdown();
    }
}
