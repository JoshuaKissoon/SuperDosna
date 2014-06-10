package dosna.osn.activitystream;

import dosna.core.ContentMetadata;
import java.util.Comparator;

/**
 * A ContentMetadata comparator for activity stream content
 *
 * @author Joshua Kissoon
 * @since 20140610
 */
public class ContentMetadataComparator implements Comparator<ContentMetadata>
{

    @Override
    public int compare(ContentMetadata o1, ContentMetadata o2)
    {
        if (o1.getKey().equals(o2.getKey()))
        {
            return 0;
        }

        return o1.getLastUpdatedTimestamp() > o2.getLastUpdatedTimestamp() ? -1 : 1;
    }

}
