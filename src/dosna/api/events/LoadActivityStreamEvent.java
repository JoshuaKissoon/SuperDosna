package dosna.api.events;

import dosna.DosnaState;
import dosna.core.ContentMetadata;
import dosna.osn.activitystream.DosnaActivityStream;
import dosna.osn.actor.Actor;
import java.util.Collection;

/**
 * Interface for the load Activity Stream Event that occurs when the activity stream is being loaded.
 *
 * The activity stream loading event is taken from the perspective of the logged in actor.
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface LoadActivityStreamEvent extends Event
{

    /**
     * Method called when the actor's connections have been loaded
     *
     * @param connections The set of connections of the given actor
     */
    public void onConnectionsLoad(final Collection<Actor> connections);

    /**
     * Called after all content of all connections of the logged in Actor has been collected.
     *
     * This method can be used to modify this collection by removing or adding possible content to be selected to be displayed on the activity stream.
     *
     * @param contentMd The metadata of the set of all content from all connections
     */
    public void onContentCollected(final Collection<ContentMetadata> contentMd);

    /**
     * After the content has been collected, an activity stream algorithm will select which content is to be displayed on the activity stream.
     * After this set of content has been selected, it can now be modified using this method.
     *
     * @param contentMd The metadata of the set of content selected to be displayed on the activity stream
     */
    public void onDisplayedContentSelected(final Collection<ContentMetadata> contentMd);

    /**
     * After the activity stream has been created, this method can now modify it if required.
     *
     * @param activityStream The activity stream
     */
    public void beforeActivityStreamDisplay(final DosnaActivityStream activityStream);
}
