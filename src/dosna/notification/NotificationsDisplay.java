package dosna.notification;

import dosna.api.notification.Notification;
import dosna.gui.util.GBConstraints;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JPanel used to display notifications
 *
 * @author Joshua Kissoon
 * @since 20140612
 */
public class NotificationsDisplay extends JPanel
{
    
    private final int DEFAULT_HEIGHT = 100;
    private final int DEFAULT_WIDTH = 100;
    
    private final NotificationBox notificationBox;

    /**
     * @param notificationBox The box of notifications
     */
    public NotificationsDisplay(final NotificationBox notificationBox)
    {
        this.notificationBox = notificationBox;
        
        this.create();
    }
    
    private void create()
    {
        this.setLayout(new GridBagLayout());
        
        JLabel lbl;
        int counter = 0;
        for (Notification n : this.notificationBox.getNotifications())
        {
            lbl = new JLabel(n.getNotificationMessage());
            this.add(lbl, GBConstraints.getItemConstraints(0, counter++));
        }
        this.setDisplaySize(new Dimension(this.DEFAULT_WIDTH, this.DEFAULT_HEIGHT));
    }

    /**
     * Set the display size of the notification box
     *
     * @param d The dimension
     */
    public void setDisplaySize(Dimension d)
    {
        this.setSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
    }
}
