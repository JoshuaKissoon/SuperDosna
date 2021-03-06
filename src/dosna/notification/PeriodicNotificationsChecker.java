package dosna.notification;

import dosna.dhtAbstraction.DataManager;
import dosna.notification.NotificationBox;
import dosna.osn.actor.Actor;
import java.io.IOException;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import socialkademlia.dht.JSocialKademliaStorageEntry;
import kademlia.exceptions.ContentNotFoundException;

/**
 * This class carries out periodic checks for new notifications for this DOSN Actor.
 *
 * We use a producer-consumer design pattern here;
 * any class interested will be notified if new notifications are available.
 *
 * @author Joshua Kissoon
 * @since 20140501
 *
 * @todo Setup the producer-consumer pattern to allow for consumers to listen for notifications
 */
public class PeriodicNotificationsChecker extends Observable
{

    private final DataManager dataManager;
    private final Actor actor;

    private final Timer timer;
    private final int period = 30 * 1000;   // in milliseconds
    private final long intialDelay = 60 * 1000; // in milliseconds

    private NotificationsTimerTask timerTask;

    /* We store a copy of the latest notification box */
    private NotificationBox notificationBox;

    /**
     * Setup the class
     *
     * @param dataManager
     * @param actor       The logged in actor
     */
    public PeriodicNotificationsChecker(final DataManager dataManager, final Actor actor)
    {
        this.dataManager = dataManager;
        this.actor = actor;

        this.timer = new Timer(true);
    }

    /**
     * Start the timer to periodically check for notifications.
     * We use a timertask to handle this.
     */
    public void startTimer()
    {
        timerTask = new NotificationsTimerTask();
        this.timer.schedule(timerTask, this.intialDelay, this.period);

    }

    public NotificationBox getNotificationBox()
    {
        return this.notificationBox;
    }

    private class NotificationsTimerTask extends TimerTask
    {

        @Override
        public void run()
        {
            /* Generate a temp box so it will generate the key  */
            NotificationBox temp = new NotificationBox(actor.getId());
            try
            {
                /* Retrieve this users notification box from the network */
                JSocialKademliaStorageEntry e = dataManager.get(temp.getKey(), temp.getType());
                notificationBox = (NotificationBox) new NotificationBox().fromSerializedForm(e.getContent());

                if (notificationBox.hasNotifications())
                {
                    /* Check if we have notifications and if we do, alert all of our observers */
                    PeriodicNotificationsChecker.this.setChanged();
                    PeriodicNotificationsChecker.this.notifyObservers();
                }
            }
            catch (ContentNotFoundException exe)
            {
                System.err.println(actor.getId() + " - PeriodicNotificationChecker - Notification box not found; BoxId: " + temp.getKey() + " Box owner: " + temp.getOwnerId());
            }
            catch (IOException ex)
            {
                System.err.println(actor.getId() + " - PeriodicNotificationChecker - Error occurred; Message: " + ex.getMessage());
            }
        }
    }

    /**
     * Shutdown the notifications checker
     */
    public void shutdown()
    {
        this.timerTask.cancel();
        this.timer.cancel();
        this.timer.purge();
    }
}
