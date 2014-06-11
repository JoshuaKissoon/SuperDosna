package dosna.api.events;

import dosna.content.DOSNAContent;

/**
 * Interface for the Event that occurs when an existing content is being updated on the DHT
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface ContentUpdateEvent extends Event
{

    /**
     * Method called just before the content is updated
     *
     * @param content The content before it is updated
     */
    public void beforeContentUpdate(final DOSNAContent content);

    /**
     * Method called just before the content is put back onto the DHT
     *
     * @param content The updated version of the content
     */
    public void afterContentUpdate(final DOSNAContent content);
}
