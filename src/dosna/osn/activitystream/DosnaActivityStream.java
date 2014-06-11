package dosna.osn.activitystream;

import java.util.Collection;

/**
 * The Activity Stream displayed to the user.
 *
 * @author Joshua Kissoon
 * @since 20140611
 */
public interface DosnaActivityStream
{

    /**
     * Set the content to be put on the activity stream
     *
     * @param content The activity stream content
     */
    public void setContent(final Collection<ActivityStreamContent> content);

    /**
     * Create the Activity Stream
     */
    public void create();
}
