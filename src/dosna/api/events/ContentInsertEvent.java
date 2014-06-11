package dosna.api.events;

import dosna.content.DOSNAContent;

/**
 * Interface for the Event that occurs when a content is being inserted into the DHT
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface ContentInsertEvent extends Event
{

    /**
     * Method called just before the content is put on the DHT
     *
     * @param content The content to be inserted
     */
    public void beforeContentInsert(final DOSNAContent content);
}
