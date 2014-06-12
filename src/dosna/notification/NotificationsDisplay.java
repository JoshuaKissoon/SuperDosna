package dosna.notification;

import dosna.api.notification.Notification;
import dosna.gui.util.GBConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
    private JScrollPane scrollPane = null;

    /**
     * @param notificationBox The box of notifications
     */
    public NotificationsDisplay(final NotificationBox notificationBox)
    {
        this.notificationBox = notificationBox;

        this.create();
    }

    /**
     * Create the full display
     */
    private void create()
    {
        this.setLayout(new GridBagLayout());

        /* Put a header */
        String headerStr = "<html><table>"
                + "<tr>"
                + "<td align='center'>"
                + "<b>Notifications!</b>"
                + "</td>"
                + "</tr>"
                + "</table></html>";
        JLabel header = new JLabel(headerStr);
        this.add(header, GBConstraints.getItemConstraints(0, 0));

        JLabel lbl;
        int counter = 1;
        for (Notification n : this.notificationBox.getNotifications())
        {
            lbl = new JLabel(n.getNotificationMessage());
            Border margin = new EmptyBorder(10, 10, 10, 10);
            Border line = new LineBorder(Color.BLACK);
            lbl.setBorder(new CompoundBorder(line, margin));
            this.add(lbl, GBConstraints.getItemConstraints(0, counter++));
        }
        this.setDisplaySize(new Dimension(this.DEFAULT_WIDTH, this.DEFAULT_HEIGHT));
    }

    public JScrollPane getNotificationsDisplay()
    {
        if (this.scrollPane == null)
        {
            this.scrollPane = new JScrollPane(this);
        }
        return this.scrollPane;
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
