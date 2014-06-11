package dosna.notification;

import dosna.api.notification.Notification;
import javax.swing.JPanel;
import kademlia.node.KademliaId;

/**
 * A notification to be added to the notification box
 *
 * @author Joshua
 * @since
 */
public class DefaultNotification implements Notification
{

    private final String notificationType = "DefaultNotification";

    private final KademliaId key;
    private final String notification;

    /**
     * Create a new notification
     *
     * @param key          The key of the content this notification is for
     * @param notification The Notification itself.
     */
    public DefaultNotification(KademliaId key, String notification)
    {
        this.key = key;
        this.notification = notification;
    }

    @Override
    public KademliaId getContentKey()
    {
        return this.key;
    }

    @Override
    public String getNotificationMessage()
    {
        return this.notification;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Notification: ");

        sb.append("[Text: ");
        sb.append(notification);
        sb.append("]");

        return sb.toString();
    }

    @Override
    public JPanel getNotificationDisplay()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getType()
    {
        return this.notificationType;
    }
}
