package dosna.api.notification;

import dosna.content.DOSNAContent;

/**
 * Class that provides methods to handle sending notifications
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public class NotificationsManager
{

    /**
     * Send a notification to a specific actor
     *
     * @param actorId The actor to send the notification to
     * @param content The content to notify about
     * @param message The notification message
     *
     * @return Whether the notification was successful or not
     */
    public boolean sendNotification(String actorId, DOSNAContent content, String message)
    {

    }

    /**
     * Send a notification to all actors related to a content
     *
     * @param content The content to notify about
     * @param message The notification message
     *
     * @return Whether the notification was successful or not
     */
    public boolean sendNotification(DOSNAContent content, String message)
    {

    }
}
