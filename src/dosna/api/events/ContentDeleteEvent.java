package dosna.api.events;

import dosna.content.DOSNAContent;

/**
 * Interface for the Event that occurs when a content is being deleted into the DHT
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface ContentDeleteEvent extends Event
{

    /**
     * Method called just before the content is deleted from the DHT
     *
     * @param content The content to be deleted
     */
    public void beforeContentDelete(final DOSNAContent content);
}
