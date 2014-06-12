package dosna.api.notification;

import dosna.content.DOSNAContent;
import dosna.dhtAbstraction.DataManager;
import dosna.notification.DefaultNotification;
import dosna.notification.NotificationBox;
import java.io.IOException;
import java.util.List;
import kademlia.exceptions.ContentNotFoundException;
import socialkademlia.dht.JSocialKademliaStorageEntry;

/**
 * Class that provides methods to handle sending notifications
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class NotificationsManager
{

    private final DataManager dataManager;

    /**
     * Setup an instance of the notification manager
     *
     * @param dataManager
     */
    public NotificationsManager(final DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Retrieve the notification box for a given actor
     *
     * @param actorId The actor whose notificaton box to retrieve
     *
     * @return The retrieved NotificationBox
     *
     * @throws java.io.IOException
     * @throws kademlia.exceptions.ContentNotFoundException
     */
    public NotificationBox getNotificationBox(final String actorId) throws IOException, ContentNotFoundException
    {
        /* Lets construct a temporary DefaultNotification Box for this actor since the key will be generated */
        NotificationBox temp = new NotificationBox(actorId);

        /* Retrieve this users notification box from the network */
        JSocialKademliaStorageEntry e = this.dataManager.get(temp.getKey(), temp.getType());
        NotificationBox original = (NotificationBox) new NotificationBox().fromSerializedForm(e.getContent());

        return original;
    }

    /**
     * Send a notification to a specific actor
     *
     * @param actorId      The actor to send the notification to
     * @param notification The notification to send
     *
     * @return Whether the notification was successful or not
     */
    public boolean sendNotification(final String actorId, final DefaultNotification notification)
    {
        /* Lets construct a temporary DefaultNotification Box for this actor since the key will be generated */
        NotificationBox temp = new NotificationBox(actorId);

        try
        {
            /* Retrieve this users notification box from the network */
            JSocialKademliaStorageEntry e = this.dataManager.get(temp.getKey(), temp.getType());
            NotificationBox original = (NotificationBox) new NotificationBox().fromSerializedForm(e.getContent());

            /* Add the updated notification box */
            original.addNotification(notification);

            /* Push the notification box back onto the network */
            dataManager.put(original);
        }
        catch (IOException | ContentNotFoundException ex)
        {
            return false;
        }

        /* We got here means everything's all good */
        return true;
    }

    /**
     * Send a notification to all actors related to a content
     *
     * @param content      The content to notify about
     * @param notification The notification to send
     *
     * @return Whether the notification was successful or not
     */
    public boolean sendNotification(final DOSNAContent content, final DefaultNotification notification)
    {
        List<String> associatedUsers = content.getRelatedActors();
        for (String actorId : associatedUsers)
        {
            /* Lets construct a temporary DefaultNotification Box for this actor since the key will be generated */
            NotificationBox temp = new NotificationBox(actorId);

            try
            {
                /* Retrieve this users notification box from the network */
                JSocialKademliaStorageEntry e = this.dataManager.get(temp.getKey(), temp.getType());
                NotificationBox original = (NotificationBox) new NotificationBox().fromSerializedForm(e.getContent());

                /* Add the updated notification box */
                original.addNotification(new dosna.notification.DefaultNotification(content.getKey(), "Content has been modified"));

                /* Push the notification box back onto the network */
                dataManager.put(original);
            }
            catch (IOException | ContentNotFoundException ex)
            {
                return false;
            }
        }

        /* We got here means everything executed successfully */
        return true;
    }
}
