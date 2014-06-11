package dosna.api.notification;

import javax.swing.JPanel;
import kademlia.node.KademliaId;

/**
 * An interface that specifies the structure of notifications
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface Notification
{

    /**
     * The notification will be displayed in the notifications area in the form of a JPanel
     *
     * @return The notification to be displayed
     */
    public JPanel getNotificationDisplay();

    /**
     * @return The type of the notification
     */
    public String getType();

    /**
     * Get the Key of the content associated with this notification
     *
     * @return The key
     */
    public KademliaId getContentKey();

    /**
     * @return The raw notification message string
     */
    public String getNotificationMessage();
}
