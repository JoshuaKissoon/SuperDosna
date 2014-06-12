package dosna.messaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A message that can be sent between users
 *
 * @author Joshua Kissoon
 * @since 20140610
 */
public class Message implements Comparable<Message>
{

    private final String text;
    private final long timestamp;

    /* The ActorId of the sender and recipient */
    private final String senderId, recipientId;

    /**
     * Create a new message
     *
     * @param text        The actual message text
     * @param senderId    The Actor Id of the sender
     * @param recipientId The Actor Id of the recipient
     */
    public Message(String text, String senderId, String recipientId)
    {
        this.text = text;
        this.timestamp = System.currentTimeMillis() / 1000L;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public String getText()
    {
        return this.text;
    }

    public String getSenderId()
    {
        return this.senderId;
    }

    public String getRecipientId()
    {
        return this.recipientId;
    }

    public long getCreateTimestamp()
    {
        return this.timestamp;
    }

    /**
     * Create the DateTime value from the timestamp
     *
     * @return Formatted Date & Time for when this message was sent
     */
    public String getDateTime()
    {
        Date date = new Date(this.getCreateTimestamp() * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("E dd MMMM, yyyy; HH:mm:ss"); // the format of your date
        String formattedDate = sdf.format(date);
        
        return formattedDate;
    }

    /**
     * Compare another message to this message.
     *
     * We compare messages based on the time they were created.
     *
     * @param m The other message to compare this message to
     */
    @Override
    public int compareTo(Message m)
    {
        if (this == m)
        {
            return 0;
        }

        return this.getCreateTimestamp() > m.getCreateTimestamp() ? 1 : -1;
    }
}
